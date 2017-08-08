/*
 * Experiment.java
 *
 * Created on 5 agosto 2007, 14.31
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.stats;

import org.springframework.beans.factory.annotation.Autowired;

import it.red.algen.context.ContextSupplier;
import it.red.algen.domain.Env;
import it.red.algen.engine.Evolver;
import it.red.algen.engine.factories.EnvFactory;
import it.red.algen.tracking.EnvObserver;




/**
 *
 *TODOM: collapse with Evolver?
 *
 * @author grossi
 */
public class Experiment {
	private EnvFactory factory;
    private ExperimentStats stats;


    @Autowired
    private ContextSupplier contextSupplier;
    
    public Experiment(EnvFactory factory) {
        this.factory = factory;
        stats = null;
    }
    
    public ExperimentStats getStats(){
        return stats;
    }
    
    public void run(){
    	
    	// Setups observer
        EnvObserver observer = new EnvObserver(contextSupplier.getContext());
        
        // Creates initial environment
        Env environment = factory.create();
    	
        // Setups engine
        Evolver evolver = new Evolver(
        		contextSupplier.getContext(), 
        		environment);
        evolver.subscribe(observer);
        
        // Starts evolution
        evolver.evolve();
        
        // Retrieves stats
        stats = evolver.getStats();
    }
}
