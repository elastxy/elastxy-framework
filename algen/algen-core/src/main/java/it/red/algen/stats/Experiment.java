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
import it.red.algen.engine.EnvFactory;
import it.red.algen.engine.Evolver;
import it.red.algen.engine.Selector;
import it.red.algen.engine.StandardSelector;
import it.red.algen.tracking.EnvObserver;




/**
 *
 *TODOM: collapse with Evolver?
 *
 * @author grossi
 */
public class Experiment {
	private EnvFactory _factory;
    private ExperimentStats _stats;


    @Autowired
    private ContextSupplier contextSupplier;
    
    public Experiment(EnvFactory factory) {
        _factory = factory;
        _stats = null;
    }
    
    public ExperimentStats getStats(){
        return _stats;
    }
    
    public void run(){
    	
    	// Setups observer
        EnvObserver observer = new EnvObserver(contextSupplier.getContext());
        
        // Setups operators
        Selector selector = new StandardSelector();
        selector.setup(contextSupplier.getContext().parameters, contextSupplier.getContext().mutator);
        selector.subscribe(observer);
        // TODOA: Recombinator
        
        // Creates initial environment
        Env environment = _factory.create();
    	
        // Setups engine
        Evolver evolver = new Evolver(contextSupplier.getContext(), environment, selector);
        evolver.subscribe(observer);
        
        // Starts evolution
        evolver.evolve();
        
        // Retrieves stats
        _stats = evolver.getStats();
    }
}
