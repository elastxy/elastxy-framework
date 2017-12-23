package org.elastxy.app.d.metaexpressions;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.elastxy.core.conf.ReadConfigSupport;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.AlleleValuesProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.Randomizer;
import org.elastxy.core.engine.metadata.GenesMetadataConfiguration;
import org.elastxy.core.engine.metadata.MetadataGenomaBuilder;
import org.elastxy.core.engine.metadata.StandardMetadataGenoma;
import org.elastxy.distributed.dataprovider.DistributedAlleleValuesProvider;
import org.elastxy.distributed.dataprovider.DistributedGenomaProvider;
import org.elastxy.distributed.dataprovider.RDDDistributedWorkingDataset;


/**
 * Transforms raw RDD<Long> data into RDD<Allele> alleles.
 * 
 * RDD<Allele> cardinality should be enough to cover 
 * a single population: solutionsNumber + 50%
 * 
 * TODOM-4: bloccare le interfacce in ottica SDK!
 * @author red
 */
public class MexdDistributedGenomaProvider implements DistributedGenomaProvider {
	private static Logger logger = Logger.getLogger(MexdDistributedGenomaProvider.class);

	private StandardMetadataGenoma cachedGenoma;

	private AlgorithmContext context;

	private RDDDistributedWorkingDataset<Long> workingDataset;
	
	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}
	
	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
		this.workingDataset = (RDDDistributedWorkingDataset<Long>)workingDataset;
	}

	
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	
	
	@Override
	public void collect() {
		
		// Retrieves metadata
		GenesMetadataConfiguration genes = ReadConfigSupport.retrieveGenesMetadata(context.application.appName);

		long totAlleles = 2 * context.algorithmParameters.initialSelectionNumber;
		if(logger.isDebugEnabled()) logger.debug(String.format("Extracting %d random alleles for population", totAlleles));
	    JavaRDD<Allele> alleles = pickNumbers(workingDataset.rdd, totAlleles).map(DataToAllele::toAllele);

		// Transform data to Alleles, exactly 2 for solutions (2 operands)
		AlleleValuesProvider valuesProvider = new DistributedAlleleValuesProvider(alleles);

		StandardMetadataGenoma genoma = MetadataGenomaBuilder.create(context);
		MetadataGenomaBuilder.setupAlleleValuesProvider(genoma, valuesProvider);
		MetadataGenomaBuilder.addGenes(genoma, genes);
		
		context.application.alleleGenerator.setup(genoma);
		MetadataGenomaBuilder.finalize(genoma);
		
		// Initialize Genoma
		cachedGenoma = genoma;
	}

	
	/**
	 * Mutation is a perc of every generation within an era, plus something for Spark (+0.3).
	 * E.g. for a 100 individuals population iterating 100 times over an era, with 0.3 mutation perc (hence 30 individuals)
	 * we need: 100 * 0.2 * 100 * 1.3 = 2600 genes
	 * 
	 * In case execution time instead generation number is provided, a number of alleles
	 * is taken proportional to partitions, plus something for Spark (+0.3).
	 * E.g. for a 100 individuals population iterating 1 minute over an era, with 0.3 mutation perc (hence 30 individuals)
	 * and a base of 
	 * - 200 alleles 		=> we need 200 / 8 * 0.3 * 1.3 = 9.75 = 10 genes.
	 * - 20000 alleles 	=> we need 20000 / 8 * 0.3 * 1.3 = 975 genes.
	 * - 2000000 alleles 	=> we need 2000000 / 8 * 0.3 * 1.3 = 97500 genes.
	 * 
	 * If total needed alleles is more than available, all are pushed.
	 * 
	 */
	@Override
	public List<Allele> collectForMutation() {
		double solutionsToMutatePerGen = ((double)context.algorithmParameters.initialSelectionNumber) * context.algorithmParameters.mutationPerc;
		long countAvailable = workingDataset.rdd.count();
		long neededAlleles = -1;
		long totAlleles = -1;
		if(context.algorithmParameters.stopConditions.maxGenerations > 0){
			neededAlleles = Math.round(solutionsToMutatePerGen * (double)context.algorithmParameters.stopConditions.maxGenerations); // a little more choice
		}
		// If iterations count is not known, by now we take a mutationPerc % of all Alleles in the partitions
		// TODOM-4: upgrade considering real elapsed generation time (by executing a benchmark, for example). Check size of memory / network compromise
		else {
			neededAlleles = Math.round(countAvailable / (double)context.algorithmParameters.partitions * context.algorithmParameters.mutationPerc);
		}
		totAlleles = neededAlleles < countAvailable ? neededAlleles : countAvailable;
		if(logger.isDebugEnabled()) logger.debug(String.format("Extracting %d random alleles for mutation (needed %d, available %d)", totAlleles, neededAlleles, countAvailable));
	    JavaRDD<Allele> mutatedGenomaRDD = pickNumbers(workingDataset.rdd, totAlleles).map(DataToAllele::toAllele);

	    List<Allele> mutatedGenomaList = mutatedGenomaRDD.collect();
	    if(logger.isDebugEnabled()){
	      logger.debug("Number of "+mutatedGenomaList.size()+" mutated genes.");
	      if(logger.isTraceEnabled()) logger.trace(mutatedGenomaList);
	    }
	    return mutatedGenomaList;
	}


	private JavaRDD<Long> pickNumbers(JavaRDD<Long> numbers, Long tot) {
	    final Long totNumbers = numbers.count();
	    final double percExtract = 1.3 * tot.doubleValue() / totNumbers.doubleValue();// put a little more // TODOM-1: spark plus configurable
	    if(logger.isDebugEnabled()) {
	      logger.debug("Picking perc "+percExtract+" of "+totNumbers+" numbers (was needed "+tot+")");
	    }
	    JavaRDD<Long> result = numbers.sample(true, percExtract, Randomizer.seed());
		// TODOA-2: check performance of caching
	    result.cache();
//	    if(logger.isDebugEnabled()) {
//	      val totPicked = result.count()
//	      logger.debug(f"Picked rdd of $totPicked numbers")
//	      Monitoring.printRDDGenoma(result)
//	      Monitoring.printPartitionsGenoma(result)
//	    }
	    return result;
	  }
	
	
	/**
	 * Does nothing: returns the whole genoma
	 */
	@Override
	public Genoma shrink(Target<?, ?> target) {
		return cachedGenoma;
	}
	
	
	@Override
	public void spread() {
		if(logger.isDebugEnabled()) logger.debug("Spreading genetic material for a new Era");
		collect();
	}


}
