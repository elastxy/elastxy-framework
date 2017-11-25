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
	    alleles.cache();

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

	
	@Override
	public List<Allele> collectForMutation() {
		long solutionsToMutate = Math.round(context.algorithmParameters.initialSelectionNumber*context.algorithmParameters.mutationPerc);
		long totAlleles = 2 * solutionsToMutate * context.algorithmParameters.reshuffleEveryEras; // a little more choice
		if(logger.isDebugEnabled()) logger.debug(String.format("Extracting %d random alleles for mutation", totAlleles));
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
	    final long totNumbers = numbers.count();
	    final double percExtract = 1.5 * tot / totNumbers;// a little more
	    // TODOD: logging
//	    if(logger.isDebugEnabled()) {
//	      logger.debug(f"Picking perc $percExtract of $totNumbers numbers (was needed $tot)")
//	    }
	    JavaRDD<Long> result = numbers.sample(true, percExtract, Randomizer.seed());
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
