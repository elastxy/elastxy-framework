/*
 * Experiment.java
 *
 * Created on 5 agosto 2007, 14.31
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.engine.core;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Env;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;




/**
 *	Local (single town) experiment.
 *
 *	Defines execution for a locally based algorithm:
 *  - local events
 *  - local populations and factory
 *  - local evolver
 *  
 *  No distributed nor concurrent features are available 
 *  within this Experiment type.
 *
 * @author grossi
 */
public class SingleTownExperiment {
    private ExperimentStats stats;

    private AlgorithmContext context;
    
    public SingleTownExperiment(AlgorithmContext context) {
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
        SingleTownEvolver evolver = new SingleTownEvolver(
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
