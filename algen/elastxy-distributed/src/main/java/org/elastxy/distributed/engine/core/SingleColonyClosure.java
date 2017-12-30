package org.elastxy.distributed.engine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;
import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.applications.components.factory.AppBootstrapRaw;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.SingleColonyExperiment;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.distributed.dataprovider.BroadcastWorkingDataset;
import org.elastxy.distributed.dataprovider.BroadcastedDatasetProvider;
import org.elastxy.distributed.dataprovider.ProcessingOnlyDistributedGenomaProvider;

/**
 * MAIN TRAITS
 * 
 * Takes one of the source data partition,
 * and uses that data for evolving a new population
 * using standard local algorithm.
 * 
 * For better efficiency, picks mutate genes from a broadcast
 * variable predetermined in colonies distributed evolver context
 * for every era iteration.
 * 
 * When result is found, long accumulator is incremented,
 * signalling to other colonies that one of the best solution is born.
 * 
 * PROCESSING-ONLY APPS
 * 
 * For processing-only applications, where input RDD partitioned alleles
 * are not meaningful and data can be retrieved locally (like Sudoku),
 * The closure leaves execution identical to local.
 * 
 * @author red
 *
 */
public class SingleColonyClosure implements FlatMapFunction<Iterator<Allele>, Solution> {
	private static Logger logger = Logger.getLogger(SingleColonyClosure.class);
	
	private long currentEraNumber = 0L;
	private AlgorithmContext context;
	private Target target;
    private LongAccumulator coloniesGoalAccumulator;
    private Broadcast<List<Allele>> mutatedGenesBC;
    private Broadcast<List<Solution>> previousBestMatchesBC;
    private Map<String, BroadcastWorkingDataset> broadcastDatasets;
    
    
    // TODOM-1: ClosureContext instead of many params
	public SingleColonyClosure(
			long currentEraNumber,
			AlgorithmContext context,
			Target target,
  			LongAccumulator coloniesGoalAccumulator,
  			Broadcast<List<Allele>> mutatedGenesBC,
  			Broadcast<List<Solution>> previousBestMatchesBC,
  			Map<String, BroadcastWorkingDataset> broadcastDatasets){
		this.currentEraNumber = currentEraNumber;
		this.context = context;
		this.target = target;
		this.coloniesGoalAccumulator = coloniesGoalAccumulator;
		this.mutatedGenesBC = mutatedGenesBC;
		this.previousBestMatchesBC = previousBestMatchesBC;
		this.broadcastDatasets = broadcastDatasets;
	}
	
	
	public Iterator<Solution> call(Iterator<Allele> initialGenomaIterator) throws Exception {

		logger.info(">>> 2.0 Bootstrapping LOCAL application context");
		bootstrap();
		
		logger.info(String.format(">>> 2.1 Population Creation [era %d] 					WORKER => List[Solution]", currentEraNumber));
	    
		boolean processingOnly = (context.application.distributedGenomaProvider instanceof ProcessingOnlyDistributedGenomaProvider) ? true : false;
		ExperimentStats stats = processingOnly ? runIsolatedColonyExperiment() : runLinkedColonyExperiment(initialGenomaIterator);
		
		if(stats.targetReached){
			logger.info(">>> TARGET GOAL REACHED!!! <<<");
			logger.info(">>> BestMatch: "+stats.lastGeneration.bestMatch+" <<<");
			this.coloniesGoalAccumulator.add(1);
		}
		List<Solution> bestMatches = new ArrayList<Solution>();
		bestMatches.addAll(stats.lastGeneration.bestMatches);
		return bestMatches.iterator();
	}

	
	/**
	 * Runs an experiment starting from scratch every time: no genoma is reintroduced
	 * (included best matches of previous eras).
	 * 
	 * @param initialGenomaIterator
	 * @return
	 */
	private ExperimentStats runIsolatedColonyExperiment() {
		
		// Import Solution from Broadcast variable => new best solutions
		List<Solution> previousBestMatches = previousBestMatchesBC==null ? null : previousBestMatchesBC.getValue();
		if(logger.isTraceEnabled()) logger.trace(String.format("Solutions from prev best matches: %.2000s", previousBestMatches));

	    // TODOM-4: Creates a local complete Genoma Provider
	    
	    // Executes local Experiment
		if(logger.isTraceEnabled()) logger.trace(String.format(">>> Single Colony Experiment Context %n%s", context));
		SingleColonyClosureExperiment experiment = new SingleColonyClosureExperiment(
				context, 
				target, 
				null,
				null,
				previousBestMatches);
		experiment.run();
		ExperimentStats stats = experiment.getStats();
		return stats;
	}

	
	/**
	 * Runs an experiment starting from the map partitions iterator.
	 * 
	 * @param initialGenomaIterator
	 * @return
	 */
	private ExperimentStats runLinkedColonyExperiment(Iterator<Allele> initialGenomaIterator) {
		
		// Import Alleles from Iterator => new population
		List<Allele> newPopulationAlleles = new ArrayList<Allele>();
	    initialGenomaIterator.forEachRemaining(newPopulationAlleles::add);
		if(logger.isTraceEnabled()) logger.trace(String.format("New population alleles: %.2000s ", newPopulationAlleles));
	    
		// Import Alleles from Broadcast variable => new mutations
		List<Allele> mutationAlleles = mutatedGenesBC.getValue();
		if(logger.isTraceEnabled()) logger.trace(String.format("Alleles for mutation: %.2000s", mutationAlleles));

		// Import Solution from Broadcast variable => new best solutions
		List<Solution> previousBestMatches = previousBestMatchesBC==null ? null : previousBestMatchesBC.getValue();
		if(logger.isTraceEnabled()) logger.trace(String.format("Solutions from prev best matches: %.2000s", previousBestMatches));

	    // TODOM-4: Creates a local complete Genoma Provider
	    
	    // Executes local Experiment
		if(logger.isTraceEnabled()) logger.trace(String.format(">>> Single Colony Experiment Context %n%s", context));
		SingleColonyClosureExperiment experiment = new SingleColonyClosureExperiment(
				context, 
				target, 
				newPopulationAlleles,
				mutationAlleles,
				previousBestMatches);
		experiment.run();
		ExperimentStats stats = experiment.getStats();
		return stats;
	}


	private void bootstrap() {
		AppBootstrapRaw bootstrap = new AppBootstrapRaw();
		AppComponentsLocator locator = bootstrap.boot(context.application.appName);
		// TODOM-1: check if mandatory configurations are present!
		logger.info("Initializing Closure LOCAL context.");
		setupContext(locator);
	}

	
	private void setupContext(AppComponentsLocator locator) {
		context.application = locator.get(context.application.appName);
		if(context.application.datasetProvider!=null) {
			context.application.datasetProvider.setup(context);
		}
		if(context.application.singleColonyDatasetProvider!=null && context.application.singleColonyDatasetProvider instanceof BroadcastedDatasetProvider) {
			((BroadcastedDatasetProvider)context.application.singleColonyDatasetProvider).setBroadcastDatasets(broadcastDatasets);
			context.application.singleColonyDatasetProvider.setup(context);
		}
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.recombinator.setup(context.algorithmParameters);
		context.application.targetBuilder.setup(context);
		context.application.envFactory.setup(context);
//		context.application.resultsRenderer.setup(context);
	}
	
}