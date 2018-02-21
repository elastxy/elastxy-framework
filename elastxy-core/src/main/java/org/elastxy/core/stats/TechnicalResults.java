package org.elastxy.core.stats;

import org.elastxy.core.domain.experiment.RawFitness;

public class TechnicalResults extends StandardExperimentResults {
	private static final long serialVersionUID = 2945734720168147500L;
	
	/**
	 * Fitness value of best match solution (0 to 1 interval).
	 */
	public Double fitnessValue;
	
	public RawFitness rawFitnessValue;
	
	public String legalCheck;

	public Object phenotypeValue;

}
