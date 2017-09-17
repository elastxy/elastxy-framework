package it.red.algen.domain.experiment;

/**
 * 
 * MULTIOBJECTIVE TARGET
 * 
 * In some cases, user needs to achieve many different goals,
 * reflected by the number of chromosomes involved, each one with its own fitness.
 * 
 * A set of weight states the proportions between those goals in contributing
 * to the global fitness values, usually driven by their importance with
 * respect to the global goal.
 * 
 * All other single goal attributes are taken from Target
 * 
 * @author red
 *
 */
public interface MultiObjectiveTarget<Solution,Object> extends Target<Solution,Object> {


	/**
	 * Ordered weights in case of multiobjective goal
	 * @return
	 */
	public Double[] getWeights();

	
	/**
	 * Set weights of multiobjective goals
	 * @param weights
	 */
	public void setWeights(Double... weights);
	
}
