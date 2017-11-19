package it.red.algen.engine.core;

import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObservable;


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
