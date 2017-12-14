package org.elastxy.core.domain.experiment;

import java.util.List;

/**
 * MULTIOBJECTIVE TARGET
 * 
 * A target can be referred to a single or a multiple objective.
 * 
 * In some cases, user needs to achieve many different goals,
 * reflected by the number of chromosomes involved, each one with its own 
 * Target and calculated fitness.
 * 
 * The stop condition in multi-objective context can be:
 * 
 * - AGGREGATE
 * An aggregate weighted value, compared to target and threshold fitness
 * of the multi-objective target.
 * A set of weights states the proportions between those goals in contributing
 * to the global fitness values, usually driven by their importance with
 * respect to the global goal.
 * If no weights are declared, every goal weights the same proportion with 
 * respect to global goal.
 * 
 * OR
 * 
 * - SEPARATE
 * Algorithm can decide to stop when all mandatory target are reached,
 * looking at the array of mandatories goal.
 * If no mandatory goal is declared, all must be reached.
 * 
 * OR
 * 
 * - BOTH
 * Both the previous options are evaluated, and first solution reaching at least one of 
 * them stops the algorithm.
 * 
 * @see Target
 * 
 * @author red
 *
 */
public interface MultipleTarget<Solution,Object> extends Target<Solution,Object> {
	
	public TargetType getTargetType();
	public void setTargetType(TargetType targetType);

	/**
	 * Returns the ordered list of targets.
	 * Positions are the same occupied by chromosomes.
	 * If targets are null or empty, evaluation is aggregate based on global target.
	 * 
	 * @return
	 */
	public List<Target> getTargetList();

	/**
	 * Sets the ordered list of targets.
	 * Positions are the same occupied by chromosomes.
	 * If targets are null or empty, evaluation is aggregate based on global target.
	 * 
	 * @param targetList
	 */
	public void setTargetList(List<Target> targetList);
	
	/**
	 * Ordered weights in case of multi-objective goal.
	 * If null or empty, all are weighted proportionally the same amount.
	 * @return
	 */
	public Double[] getWeights();

	
	/**
	 * Set weights of multiobjective goals.
	 * If null or empty, all are weighted proportionally the same amount.
	 * 
	 * @param weights
	 */
	public void setWeights(Double... weights);
	

	/**
	 * Needed targets in case of multi-objective goal.
	 * If null or empty, all are needed.
	 * 
	 * @return
	 */
	public Boolean[] getNeededTargetList();

	
	/**
	 * Set needed target in a multiobjective goals.
	 * If null or empty, all are needed.
	 * 
	 * @param weights
	 */
	public void setNeededTargetList(Boolean... needed);
}
