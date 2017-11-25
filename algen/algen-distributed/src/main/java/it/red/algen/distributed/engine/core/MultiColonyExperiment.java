/*
 * Experiment.java
 *
 * Created on 5 agosto 2007, 14.31
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.distributed.engine.core;

import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.distributed.engine.factory.MultiColonyEnvFactory;
import it.red.algen.distributed.experiment.MultiColonyEnv;
import it.red.algen.distributed.stats.MultiColonyExperimentStats;
import it.red.algen.engine.core.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;




/**
 * 
 *  INTRO
 *  
 *	Distributed (multiple colonies) experiment.
 *
 *	Defines execution for a distributed based algorithm:
 *  - distributed events
 *  - distributed populations and factory
 *  - distributed evolver which collects evolutions of many colonies over eras
 *  
 *  The default implementation adopts Spark as distributed computing framework,
 *  where Driver maintains algorithm execution and references to RDD, 
 *  while Workers evolve separate populations over time, that reshuffle from time to time 
 *  (every N eras) to spread genetic materials while preserving best matches.
 *  
 *  Every iteration on a single colony should include a number of alleles for mutating
 *  a percentage of genes.
 *  
 *  
 * ALGORITHM
 * 
 * EnvFactory
 * --------------------
 * 0.1 WorkingSet Creation 						DRIVER => RDD[Long]
 * 
 * 0.2 Partitions Creation / Shuffle 	DRIVER => Accumulator
 * 		>> DatasetProvider::collect()
 * 		>> DatasetProvider::shrink()
 * 		>> DatasetProvider::getWorkingDataset()
 * 
 * 0.3 Genoma Extraction (Population) WORKER <= RDDp[Allele]
 * 		>> GenomaProvider::setWorkingDataset(-)
 * 		>> GenomaProvider::collect()
 * 		>> GenomaProvider::getGenoma()
 * 		>> createAccumulator()
 * 
 * MultiColonyEvolver
 * --------------------
 * LOOP
 * 1.1 Eras Loop (Closure)						DRIVER => RDD[Long]
 * 1.2 Genoma Extraction (Mutation)		DRIVER <= RDDp[Long] DRIVER => Broadcast[Long]
 * 		2.1 Population Creation 					WORKER => List[Solution]
 * 		LOOP
 * 			2.2 Population Selection					WORKER => List[Solution]
 * 			2.3 Genetic Operators							WORKER => List[Solution]
 * 			2.4 Fitness Calculation						WORKER => List[Solution] WORKER => List[Best]
 * 			2.5 Check End Condition						WORKER => Accumulator
 * 		IF NOT ENDED ...back to Selection	2.2
 * 1.3 Era Iteration Best Match				DRIVER <= RDDb[Best] DRIVER => BEST MATCH
 * 1.4 Era Check End Condition				DRIVER <= Accumulator
 * IF NOT ENDED
 * 1.5 Reshuffle and Genoma Extraction (Mutation) 
 * 	DRIVER => RDD[Long] DRIVER <= RDDp[Long] DRIVER => Broadcast[Long]
 *  
 *
 * @author grossi
 */
public class MultiColonyExperiment implements Experiment {
    private MultiColonyExperimentStats stats;

    private transient DistributedAlgorithmContext context;
    
    public MultiColonyExperiment(DistributedAlgorithmContext context) {
        this.context = context;
        stats = null;
    }
    
    public ExperimentStats getStats(){
        return stats;
    }
    
    public void run(){
    	
    	// Observer setup
    	// TODOD: distributed Observer (Kafka?)
        EnvObserver observer = new EnvObserver(context);
        
        // Creates initial environment
        MultiColonyEnvFactory envFactory = (MultiColonyEnvFactory)context.application.multiColonyEnvFactory;
        MultiColonyEnv environment = (MultiColonyEnv)envFactory.createEnv();
    	
        // Setups engine
        MultiColonyEvolver evolver = new MultiColonyEvolver(
        		context, 
        		environment);
        evolver.subscribe(observer);
        
        // Starts evolution
        evolver.evolve();
        
        // Retrieves stats
        stats = (MultiColonyExperimentStats)evolver.getStats();
    }
    
    public String toString(){
    	return String.format("Experiment stats: %s", stats);
    }
}
