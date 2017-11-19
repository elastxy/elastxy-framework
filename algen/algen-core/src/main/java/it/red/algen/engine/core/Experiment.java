package it.red.algen.engine.core;

import it.red.algen.stats.ExperimentStats;

/**
 * Represents a generic genetic algorithm execution.
 * 
 * @author red
 *
 */
public interface Experiment {

	/**
	 * Process a single execution.
	 */
	public void run();
    
	/**
	 * Return simple statistics collected during execution.
	 * @return
	 */
    public ExperimentStats getStats();
    
}
