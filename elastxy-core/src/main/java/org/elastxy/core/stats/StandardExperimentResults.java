package org.elastxy.core.stats;

import java.io.Serializable;

public class StandardExperimentResults implements Serializable {
	private static final long serialVersionUID = -3136599307752592810L;

	/**
	 * String representation of target, if needed.
	 */
	public String target;
	
	/**
	 * True if goal has been reached.
	 */
    public boolean goalReached; // TODO2-2: more details of how has finished
    
    
    /**
     * Total number of iterations (generations) needed 
     * for finishing the experiment.
     */
	public long iterationsNumber;

	
	/**
	 * Total experiment execution time in ms.
	 */
	public long totalExecutionTimeMs;

	
	/**
	 * Additional info to be eventually reported.
	 */
    public String notes;
	
}
