package org.elastxy.core.engine.core;

import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObservable;


/**
 * Generic abstraction representing the genetic algorithm evolution.
 * 
 * Implementors must provide evolution logics (local, distributed,
 * over many eras or in a single succession of generations, etc.).
 * 
 * Returns an ExperimentStats bean with main statistics
 * related to this evolution and collected by algorithm.
 * 
 * @author red
 *
 */
public interface Evolver extends EnvObservable {
    
	public void evolve();
	
    public ExperimentStats getStats();
    
    
}
