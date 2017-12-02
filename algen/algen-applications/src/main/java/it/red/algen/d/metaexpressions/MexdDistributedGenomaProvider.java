package it.red.algen.d.metaexpressions;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.AlleleValuesProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.dataprovider.DistributedGenomaProvider;
import it.red.algen.distributed.dataprovider.DistributedAlleleValuesProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.core.Randomizer;
import it.red.algen.engine.metadata.GenesMetadataConfiguration;
import it.red.algen.engine.metadata.MetadataGenomaBuilder;
import it.red.algen.engine.metadata.StandardMetadataGenoma;


/**
 * Transforms raw RDD<Long> data into RDD<Allele> alleles.
 * 
 * RDD<Allele> cardinality should be enough to cover 
 * a single population: solutionsNumber + 50%
 * 
 * TODOD: bloccare le interfacce in ottica SDK!
 * @author red
 */
public class MexdDistributedGenomaProvider implements DistributedGenomaProvider {
	private static Logger logger = Logger.getLogger(MexdDistributedGenomaProvider.class);

	private StandardMetadataGenoma cachedGenoma;

	private AlgorithmContext context;

	private MexdDistributedWorkingDataset workingDataset;
	
	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}
	
	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
		this.workingDataset = (MexdDistributedWorkingDataset)workingDataset;
	}

	
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	
	
	@Override
	public void collect() {
		
		// Retrieves metadata
		GenesMetadataConfiguration genes = ReadConfigSupport.retrieveGenesMetadata(context.application.name);

		long totAlleles = 2 * context.algorithmParameters.initialSelectionNumber;
		if(logger.isDebugEnabled()) logger.debug(String.format("Extracting %d random alleles for population", totAlleles));
	    JavaRDD<Allele> alleles = pickNumbers(workingDataset.numbersRDD, totAlleles).map(DataToAllele::toAllele);

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
	 * TODOD: check size of memory / network compromise for allele to mutate!
	 * 
	 */
	@Override
	public List<Allele> collectForMutation() {
		double solutionsToMutatePerGen = ((double)context.algorithmParameters.initialSelectionNumber) * context.algorithmParameters.mutationPerc;
		long countAvailable = workingDataset.numbersRDD.count();
		// TODOA: what to do when time instead maxIterations is requested? 
		// If iterations count is not known, by now we take a mutationPerc % of all Alleles in the partitions
		long neededAlleles = -1;
		long totAlleles = -1;
		if(context.algorithmParameters.stopConditions.maxIterations > 0){
			neededAlleles = Math.round(solutionsToMutatePerGen * (double)context.algorithmParameters.stopConditions.maxIterations); // a little more choice
		}
		else {
			neededAlleles = Math.round(countAvailable / (double)context.algorithmParameters.partitions * context.algorithmParameters.mutationPerc);
		}
		totAlleles = neededAlleles < countAvailable ? neededAlleles : countAvailable;
		if(logger.isDebugEnabled()) logger.debug(String.format("Extracting %d random alleles for mutation (needed %d, available %d)", totAlleles, neededAlleles, countAvailable));
	    JavaRDD<Allele> mutatedGenomaRDD = pickNumbers(workingDataset.numbersRDD, totAlleles).map(DataToAllele::toAllele);

	    List<Allele> mutatedGenomaList = mutatedGenomaRDD.collect();
	 // TODOD: logging
//	    if(logger.isDebugEnabled()){
//	      val count = mutatedGenomaList.size
//	      logger.debug(s"List of $count mutated genes")
//	      if(logger.isTraceEnabled()) logger.trace(mutatedGenomaList)
//	    }
	    return mutatedGenomaList;
	}


	private JavaRDD<Long> pickNumbers(JavaRDD<Long> numbers, Long tot) {
	    final Long totNumbers = numbers.count();
	    final double percExtract = 1.3 * tot.doubleValue() / totNumbers.doubleValue();// a little more TODOD: spark plus configurable
	    // TODOD: logging
//	    if(logger.isDebugEnabled()) {
//	      logger.debug(f"Picking perc $percExtract of $totNumbers numbers (was needed $tot)")
//	    }
	    JavaRDD<Long> result = numbers.sample(true, percExtract, Randomizer.seed());
		// TODOD: check performance when caching after count()
//	    result.cache();
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
