package org.elastxy.distributed.engine.core;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.spark.broadcast.Broadcast;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.BestMatchesSupport;
import org.elastxy.core.engine.core.Evolver;
import org.elastxy.core.engine.fitness.FitnessComparator;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObserver;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.dataprovider.DistributedAlleleValuesProvider;
import org.elastxy.distributed.dataprovider.DistributedDatasetProvider;
import org.elastxy.distributed.dataprovider.DistributedGenomaProvider;
import org.elastxy.distributed.engine.factory.StandardMultiColonyEnvFactory;
import org.elastxy.distributed.experiment.MultiColonyEnv;
import org.elastxy.distributed.tracking.MultiColonyEnvObserver;


/**
 * Evolver implementation that spans execution over a cluster,
 * distributing evolution to many parallel colonies (nodes).
 * 
 * Iteratively, an era is created with an initial population
 * within a partition on a node (colony), and within an era
 * the local Evolver is executed a number of times as a closure.
 * 
 * An Accumulator variable checks if a colony has the best match,
 * and stops the algorithm consequently.
 * 
 * @author red
 *
 */
public class MultiColonyEvolver implements Evolver {
	private static Logger logger = Logger.getLogger(MultiColonyEvolver.class.getName());

	// ALGORITHM PARAMETERS
    public DistributedAlgorithmContext context;

    // WORKING DATA
    private MultiColonyEnv env;

    // LISTENER
    private MultiColonyEnvObserver observer;
    
    
    /**
     * @param context
     * @param env
     */
    public MultiColonyEvolver(DistributedAlgorithmContext context, MultiColonyEnv env){
    	this.context = context;
    	this.env = env;
    }
    
    
    public void subscribe(EnvObserver l){
        observer = (MultiColonyEnvObserver)l;
    }

    public ExperimentStats getStats(){
    	return MultiColonyEnvSupport.getStats(env);
    }
    
    
	/**
	 * ============================================================
	 * 		EVOLUTION
	 * ============================================================
	 * 
	 * Starts system life.
	 * 
     */
    public void evolve(){
    	
    	// Start global timer
        logger.info(">>> 0. START EXPERIMENT");
    	MultiColonyEnvSupport.startTime(env);
    	fireEvolutionStarted();
    	
    	// Create accumulator
        env.goalAccumulator = Optional.of(context.distributedContext.sc().longAccumulator(MultiColonyEnv.ACCUMULATOR_NAME));

        logger.info(">>> 1.0 Eras Loop");
        boolean stop = false;
        while(!stop) {
        	
            logger.info(String.format(">>> 1.1 Started loop for [era %d]", env.currentEraNumber));
        	fireEraStarted(env.currentEraNumber);
        	
            logger.info(String.format(">>> 1.2 Genoma Extraction (Mutation) [era %d]",env.currentEraNumber));
            prepareGenoma();
            
          	logger.info(String.format(">>> 1.3 Era Iteration Best Match [era %d]				DRIVER <= RDDb[Best] DRIVER => BEST MATCH", env.currentEraNumber));
          	executeMultiColonyEra();
            
            logger.info(String.format(">>> 1.4 Era Check End Condition [era %d]				DRIVER <= Accumulator", env.currentEraNumber));
            manageBestMatches();
            stop = checkEndCondition();
            
            if(!stop){
            	
            	// CHECK EON PROGRESS
            	checkEonProgress();
            	
            	// INCREMENT ERA
            	env.currentEraNumber++;
            }
          	
        }
        
		logger.info(String.format(">>> 2. View results"));
        viewResults(env.allBestMatches);
        fireEvolutionEndedEvent(env.targetReached, env.totIdenticalFitnesses);

        // END OF EXPERIMENT
        logFinal(">>> 3. END OF EXPERIMENT!");
    }
    
    

	private void prepareGenoma() {
		List<Allele> alleles = ((DistributedGenomaProvider)context.application.distributedGenomaProvider).collectForMutation();
		env.mutationGenesBroadcast = Optional.of(context.distributedContext.broadcast(alleles));
		env.broadcastWorkingDatasets = ((DistributedDatasetProvider)context.application.distributedDatasetProvider).getBroadcastDatasets();
	}
    

	private void executeMultiColonyEra() {
		// TODO2-1: log partitions as in Scala sandbox
		// if(logger.isTraceEnabled()) Monitoring.printPartitionsGenoma(initialGenomaRDD)
		DistributedAlleleValuesProvider alleleValuesProvider = (DistributedAlleleValuesProvider)env.genoma.getAlleleValuesProvider();
		
		// NOTE: context passed must be serializable and will be copied to new Java Runtime!
		Broadcast<List<Solution>> prevBest = env.previousBestMatchesBroadcast.isPresent() ? env.previousBestMatchesBroadcast.get() : null;
		
		env.bestMatchesRDD = alleleValuesProvider.rdd().mapPartitions(new SingleColonyClosure(
		    env.currentEraNumber,
		    context,
		    env.target,
		    env.goalAccumulator.get(),
		    env.mutationGenesBroadcast.get(),
		    prevBest,
		    env.broadcastWorkingDatasets
		    ), true);
		
		if(logger.isDebugEnabled()) logger.debug(">>>>>> Era ACTION START");
		env.eraBestMatches = env.bestMatchesRDD.collect();
		
        if(logger.isDebugEnabled()) logger.debug(String.format("     Era best matches %s", env.eraBestMatches));
    	fireEraEnded(env.currentEraNumber);
        if(logger.isDebugEnabled()) logger.debug(">>>>>> Era ACTION END");
//        if(logger.isTraceEnabled()) Monitoring.printPartitionsSolutions(bestRDD)
	}



	private void manageBestMatches() {
		long eliteNumber = BestMatchesSupport.calculateBestMatchesNumber(
				context.algorithmParameters.elitism.multiColonyElitismNumber, 
				context.algorithmParameters.elitism.multiColonyElitismPerc, 
				context.algorithmParameters.initialSelectionNumber);
		env.allBestMatches.addAll(env.eraBestMatches);
		env.allBestMatches = env.allBestMatches.stream().
				sorted(new FitnessComparator()).
				limit(eliteNumber).
				collect(Collectors.toList());
		
		if(logger.isInfoEnabled()){
			Solution eraBestMatch = env.eraBestMatches==null || env.eraBestMatches.isEmpty() ? 
					null : 
					env.eraBestMatches.stream().
						sorted(new FitnessComparator()).
						collect(Collectors.toList()).get(0);
			logger.info(String.format(">>> 		Era %d best match: %30.200s", env.currentEraNumber, eraBestMatch)); 
			Solution allBestMatch = env.allBestMatches==null || env.allBestMatches.isEmpty() ? null : env.allBestMatches.get(0);
			logger.info(String.format(">>> 		All eras best match: %30.200s", allBestMatch)); 
		}
		
		if(context.algorithmParameters.elitism.multiColonyElitism) env.previousBestMatchesBroadcast = Optional.of(context.distributedContext.broadcast(env.allBestMatches));
		if(logger.isDebugEnabled()) logger.debug(String.format("     New all best matches %s", env.allBestMatches));
	}

    private boolean checkEndCondition(){
        boolean result = false;
    	if(checkColoniesGoal() || env.currentEraNumber >= context.algorithmParameters.stopConditions.maxEras-1){
        	// TODO2-2: new stop condition: check max eras identical fitnesses
          logger.info("   >>> End condition found! Execution will be stopped.");
          fireTargetReachedEvent(null); // TODO2-2: pass stats
          result = true;
        }
    	return result;
    }
    

    private boolean checkColoniesGoal() {
        return env.goalAccumulator.isPresent() && env.goalAccumulator.get().value() > 0;
    }
	

    // TODO3-1: evaluate a maxEons concept and replace maxEras with erasPerEon
	private void checkEonProgress() {
		if((env.currentEraNumber+1) % context.algorithmParameters.reshuffleEveryEras == 0){
			logger.info(String.format("   >>> 1.5 New Eon repartition required [era %d]", env.currentEraNumber));
			fireReshuffleEvent(env.currentEraNumber);
		    // TODO3-4: Elitism: maintain best matches over reshuffle?
		    ((StandardMultiColonyEnvFactory)context.application.multiColonyEnvFactory).newEon(env);
		}
	}

    
    
    // TODO1-2: ResultsRenderer: create a specific multicolony ResultsRenderer
    private void viewResults(List<Solution> bestMatches){
        logFinal("-----------------------------------------------");
        final Long goalAccumulator = env.goalAccumulator.isPresent() ? env.goalAccumulator.get().value() : null;
        logFinal(String.format("Colonies reached goal count: %s", goalAccumulator));
        logFinal("");
        if(bestMatches.size()>0){
	        logFinal("Found best match: ");
	        logFinal(context.application.solutionRenderer.render(bestMatches.get(0)).toString());
	        logFinal("");
	        if(bestMatches.size()>1){
		        logFinal("Other Best matches: ");
		        logFinal(bestMatches.subList(1,bestMatches.size()).toString());
		        logFinal("");
	        }
	        else {
		        logFinal("Only one best match found!");
		        logFinal("");
	        }
        }
        else {
            logFinal("No best match found!");
        }
        logFinal("-----------------------------------------------");
      }
      
      private void logFinal(String msg){
        logger.info(msg);
        System.out.println(msg);
      }


    
      /**
       * ============================================================
       * 		EVENTS
       * ============================================================
       */
      private void fireEvolutionStarted(){
          observer.evolutionStartedEvent();
      }
      
      private void fireEraStarted(long eraNumber){
    	  observer.eraStartedEvent(eraNumber);
      }
      
      private void fireEraEnded(long eraNumber){
    	  observer.eraEndedEvent(eraNumber);
      }
      
      private void fireTargetReachedEvent(ExperimentStats stats){
      	observer.targetReachedEvent(stats);
      }
      
      private void fireReshuffleEvent(long eraNumber){
      	observer.reshuffleEvent(eraNumber);
      }
      
      private void fireEvolutionEndedEvent(boolean targetReached, int totIdenticalFitnesses){
      	observer.evolutionEndedEvent(targetReached, totIdenticalFitnesses);
      }
    
    public String toString(){
    	return String.format("Evolver: current Env %s", env);
    }


}
