package org.elastxy.core.engine.core;

import org.elastxy.core.stats.ExperimentStats;

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
