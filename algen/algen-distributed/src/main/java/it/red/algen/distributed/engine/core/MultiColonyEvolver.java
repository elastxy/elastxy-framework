package it.red.algen.distributed.engine.core;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.spark.broadcast.Broadcast;

import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.distributed.dataprovider.DistributedAlleleValuesProvider;
import it.red.algen.distributed.dataprovider.DistributedDatasetProvider;
import it.red.algen.distributed.dataprovider.DistributedGenomaProvider;
import it.red.algen.distributed.engine.factory.StandardMultiColonyEnvFactory;
import it.red.algen.distributed.experiment.MultiColonyEnv;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.core.Evolver;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;


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
    // TODOA-2: Add observer of MultiColonyEvolver. Extend events for MultiColonyEvolver
    private EnvObserver observer;
    
    
    /**
     * @param context
     * @param env
     */
    public MultiColonyEvolver(DistributedAlgorithmContext context, MultiColonyEnv env){
    	this.context = context;
    	this.env = env;
    }
    
    
    public void subscribe(EnvObserver l){
        observer = l;
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
    	MultiColonyEnvSupport.startTime(env);
    	
    	// Create accumulator
        env.goalAccumulator = Optional.of(context.distributedContext.sc().longAccumulator(MultiColonyEnv.ACCUMULATOR_NAME));

        logger.info(">>> 1.1 Eras Loop");
        boolean stop = false;
        while(!stop) {
            logger.info(String.format(">>> 1.1 Started loop for [era %d]", env.currentEraNumber));
        	
            logger.info(String.format(">>> 1.2 Genoma Extraction (Mutation) [era %d]",env.currentEraNumber));
            List<Allele> alleles = ((DistributedGenomaProvider)context.application.distributedGenomaProvider).collectForMutation();
            env.mutationGenesBroadcast = Optional.of(context.distributedContext.broadcast(alleles));
            env.broadcastWorkingDatasets = ((DistributedDatasetProvider)context.application.distributedDatasetProvider).getBroadcastDatasets();
            
          	logger.info(String.format(">>> 1.3 Era Iteration Best Match [era %d]				DRIVER <= RDDb[Best] DRIVER => BEST MATCH", env.currentEraNumber));
          	// TODOM-1: logging
//          	if(logger.isTraceEnabled()) Monitoring.printPartitionsGenoma(initialGenomaRDD)
          	DistributedAlleleValuesProvider alleleValuesProvider = (DistributedAlleleValuesProvider)env.genoma.getAlleleValuesProvider();
          	
          	// NOTE: context passed must be serializable and will be copied to new Java Runtime!
          	Broadcast<List<Solution>> prevBest = env.previousBestMatchesBroadcast.isPresent() ? env.previousBestMatchesBroadcast.get() : null;
			// TODOA-2: algorifero is broadcasting every time... how to persist/unpersist locally?
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
            if(logger.isDebugEnabled()) logger.debug(">>>>>> Era ACTION END");
//            if(logger.isTraceEnabled()) Monitoring.printPartitionsSolutions(bestRDD)
            Solution eraBestMatch = env.eraBestMatches==null || env.eraBestMatches.isEmpty() ? null : env.eraBestMatches.get(0);
            logger.info(String.format(">>> 		Era %d best match: %30.200s", env.currentEraNumber, eraBestMatch)); 
            logger.info(String.format(">>> 1.4 Era Check End Condition [era %d]				DRIVER <= Accumulator", env.currentEraNumber));
            env.allBestMatches.addAll(env.eraBestMatches);
            env.allBestMatches = env.allBestMatches.stream().sorted(
            		new Comparator<Solution>() {
            			public int compare(Solution a, Solution b) {
            				return (int)Math.signum(b.getFitness().getValue().subtract(a.getFitness().getValue()).doubleValue());
            			}
            		}).
            		limit(context.algorithmParameters.eraBestMatches).
            		collect(Collectors.toList());
            if(context.algorithmParameters.multicolonyElitarism) env.previousBestMatchesBroadcast = Optional.of(context.distributedContext.broadcast(env.allBestMatches));
            if(logger.isDebugEnabled()) logger.debug(String.format("     New all best matches %s", env.allBestMatches));
            
            // TODOM-2: max number of eras with same best match
            if(env.currentEraNumber >= context.algorithmParameters.stopConditions.maxEras || checkColoniesGoal()){
            	// TODOM-2: new stop condition: check max eras identical fitnesses
              logger.info("   >>> End condition found! Execution will be stopped.");
              stop = true;
            }
            
            // 1.5 Reshuffle
            else {
              if(env.currentEraNumber % context.algorithmParameters.reshuffleEveryEras == 0){
                logger.info(String.format("   >>> 1.5 Repartition required [era %d]", env.currentEraNumber));
                // TODOA-4: reinsert best matches between reshuffled eras
                env = ((StandardMultiColonyEnvFactory)context.application.multiColonyEnvFactory).newEra(env);
              }
            }
            
          	
            env.currentEraNumber++;
        }
            
        // 4. View results
        viewResults(env.allBestMatches);
        
    	logFinal("END!");
    	
        // END OF EXPERIMENT
    }

    private boolean checkColoniesGoal() {
        return env.goalAccumulator.isPresent() && env.goalAccumulator.get().value() > 0;
      }

    
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


    
//	/**
//	 * ============================================================
//	 * 		EVENTS
//	 * ============================================================
//	 */
//
//    private void fireNewEraEvent(Population lastGen, Population newGen){
//        observer.newGenerationEvent(env.currentGenNumber, EnvSupport.getLifeTimeInMillis(env), lastGen, newGen);
//    }
//
//    private void fireCrossoverEvent(Solution father, Solution mother, List<Solution> sons){
//    	observer.crossoverEvent(father, mother, sons);
//    }
//    
//    private void fireMutationEvent(Solution orig, Solution mutated){
//    	observer.mutationEvent(orig, mutated);
//    }
//    
//    private void fireGoalReachedEvent(){
//        observer.goalReachedEvent(this);
//    }
//
//    private void fireStableSolutionEvent(){
//        observer.stableSolutionEvent(this);
//    }
//    
//    private void fireHistoryEndedEvent(){
//        observer.historyEndedEvent(this);
//    }
    
    
    public String toString(){
    	return String.format("Evolver: current Env %s", env);
    }
    
}
