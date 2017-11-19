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

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Env;
import it.red.algen.engine.core.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;




/**
 * 
 *  INTRO
 *  
 *	Distributed (multiple towns) experiment.
 *
 *	Defines execution for a distributed based algorithm:
 *  - distributed events
 *  - distributed populations and factory
 *  - distributed evolver which collects evolutions of many villages over eras
 *  
 *  The default implementation adopts Spark as distributed computing framework,
 *  where Driver maintains algorithm execution, while Workers evolve separated
 *  populations over time, that from time to time (every N eras) reshuffle
 *  to spread genetic materials while preserving best matches.
 *  
 *  
 * ALGORITHM
 * 
 * EnvFactory
 * --------------------
 * 0.1 WorkingSet Creation 						DRIVER => RDD[Long]
 * 0.2 Partitions Creation / Shuffle 	DRIVER => Accumulator
 * 0.3 Genoma Extraction (Population) WORKER <= RDDp[Long]
 * 
 * MultipleTownsEvolver
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
public class MultipleTownsExperiment implements Experiment {
    private ExperimentStats stats;

    private AlgorithmContext context;
    
    public MultipleTownsExperiment(AlgorithmContext context) {
        this.context = context;
        stats = null;
    }
    
    public ExperimentStats getStats(){
        return stats;
    }
    
    public void run(){
    	
    	// Setups observer
        EnvObserver observer = new EnvObserver(context);
        
        // Creates initial environment
        Env environment = context.application.envFactory.create();
    	
        // Setups engine
        MultipleTownsEvolver evolver = new MultipleTownsEvolver(
        		context, 
        		environment);
        evolver.subscribe(observer);
        
        // Starts evolution
        evolver.evolve();
        
        // Retrieves stats
        stats = evolver.getStats();
    }
    
    public String toString(){
    	return String.format("Experiment stats: %s", stats);
    }
}
