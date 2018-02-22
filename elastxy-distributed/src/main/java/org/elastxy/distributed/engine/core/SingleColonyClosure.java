/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.distributed.context.SingleColonyClosureContext;
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

	
	private SingleColonyClosureContext closureContext;
    
	public SingleColonyClosure(SingleColonyClosureContext closureContext){
		this.closureContext = closureContext;
	}
	
	
	public Iterator<Solution> call(Iterator<Allele> initialGenomaIterator) throws Exception {

		logger.info(">>> 2.0 Bootstrapping LOCAL application context & check parameters");
		closureContext.check();
		bootstrap();
		
		logger.info(String.format(">>> 2.1 Population Creation [era %d] 					WORKER => List[Solution]", closureContext.currentEraNumber));
	    
		boolean processingOnly = (closureContext.algorithmContext.application.distributedGenomaProvider instanceof ProcessingOnlyDistributedGenomaProvider) ? true : false;
		logger.info(String.format(">>> 2.2 Executing local experiment [era %d, processing only: %b] 		WORKER => List[Solution] WORKER => List[Best]", closureContext.currentEraNumber, processingOnly));
		ExperimentStats stats = processingOnly ? runIsolatedColonyExperiment() : runLinkedColonyExperiment(initialGenomaIterator);
		
		logger.info(String.format(">>> 2.3 Check end condition [era %d] 					WORKER => Accumulator", closureContext.currentEraNumber));
		if(stats.targetReached){
			logger.info(">>> TARGET GOAL REACHED!!! <<<");
			logger.info(">>> BestMatch: "+stats.bestMatch+" <<<");
			closureContext.coloniesGoalAccumulator.add(1);
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
		List<Solution> previousBestMatches = closureContext.previousBestMatchesBC==null ? null : closureContext.previousBestMatchesBC.getValue();
		if(logger.isTraceEnabled()) logger.trace(String.format("Solutions from prev best matches: %.2000s", previousBestMatches));

	    // TODO3-4: Creates a local complete Genoma Provider (???)
	    
	    // Executes local Experiment
		if(logger.isTraceEnabled()) logger.trace(String.format(">>> Single Colony Experiment Context %n%s", closureContext.algorithmContext));
		SingleColonyClosureExperiment experiment = new SingleColonyClosureExperiment(
				closureContext.algorithmContext, 
				closureContext.target, 
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
		List<Allele> mutationAlleles = closureContext.mutatedGenesBC.getValue();
		if(logger.isTraceEnabled()) logger.trace(String.format("Alleles for mutation: %.2000s", mutationAlleles));

		// Import Solution from Broadcast variable => new best solutions
		List<Solution> previousBestMatches = closureContext.previousBestMatchesBC==null ? null : closureContext.previousBestMatchesBC.getValue();
		if(logger.isTraceEnabled()) logger.trace(String.format("Solutions from prev best matches: %.2000s", previousBestMatches));

	    // TODO3-4: Creates a local complete Genoma Provider (???)
	    
	    // Executes local Experiment
		if(logger.isTraceEnabled()) logger.trace(String.format(">>> Single Colony Experiment Context %n%s", closureContext.algorithmContext));
		SingleColonyClosureExperiment experiment = new SingleColonyClosureExperiment(
				closureContext.algorithmContext, 
				closureContext.target, 
				newPopulationAlleles,
				mutationAlleles,
				previousBestMatches);
		experiment.run();
		ExperimentStats stats = experiment.getStats();
		return stats;
	}


	private void bootstrap() {
		AppBootstrapRaw bootstrap = new AppBootstrapRaw();
		AppComponentsLocator locator = bootstrap.boot(closureContext.algorithmContext.application.appName);
		logger.info("Initializing Closure LOCAL context.");
		setupContext(locator);
	}

	
	private void setupContext(AppComponentsLocator locator) {
		AlgorithmContext ctx = closureContext.algorithmContext;
		ctx.application = locator.get(ctx.application.appName);
		if(ctx.application.datasetProvider!=null) {
			ctx.application.datasetProvider.setup(ctx);
		}
		if(ctx.application.singleColonyDatasetProvider!=null && ctx.application.singleColonyDatasetProvider instanceof BroadcastedDatasetProvider) {
			((BroadcastedDatasetProvider)ctx.application.singleColonyDatasetProvider).setBroadcastDatasets(closureContext.broadcastDatasets);
			ctx.application.singleColonyDatasetProvider.setup(ctx);
		}
		ctx.application.genomaProvider.setup(ctx);
		ctx.application.selector.setup(ctx);
		ctx.application.recombinator.setup(ctx.algorithmParameters);
		ctx.application.targetBuilder.setup(ctx);
		ctx.application.envFactory.setup(ctx);
//		context.application.resultsRenderer.setup(context);
	}
	
}
