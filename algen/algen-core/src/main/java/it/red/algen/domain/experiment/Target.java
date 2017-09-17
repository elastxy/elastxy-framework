/*
 * Target.java
 *
 * Created on 4 agosto 2007, 13.50
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain.experiment;

import java.math.BigDecimal;

/**
 * 
 * The goal toward which the whole algorithm evolves.
 * 
 * It can be expressed in any form: from a number to be calculated
 * as a result, to a complex structure to adhere to, or something to build.
 * 
 * Therefore, target is represented by specific types of goal:
 * - an undefined (even non-structured) target representation
 * or 
 * - an ideal solution to evolve toward
 * 
 * The performance of a solution against target is evaluated by measuring
 * the distance between solution and target, thus target must provide
 * something measurable to check (a "performance" to achieve, or a benchmark fitness).
 * 
 * This measure comes from environment contraints and characteristics,
 * and can be used globally in an experiment to simplify 
 * the fitness calculations for other solutions when compared to target.
 * 
 * Thus, target is often synthesized with a simple piece of information,
 * for avoiding to recalculate it everytime.
 * 
 * Fitness and goal:
 * - target fitness is the "exact" value to achieve (or to tends toward)
 * E.g. 0.5 should reflect the middle of a optimum fitness
 * - target threshold is the "minimum" value to achieve, execution is then stopped
 * 
 * @author grossi
 */
public interface Target<G,M> {

	/**
	 * The goal to achieve in its raw format
	 * 
	 * E.g. a text string, a mathematic function, a sudoku complete matrix, a number of recipes..
	 * 
	 * @return
	 */
	public G getGoal();
	public void setGoal(G goal);
	
	
	/**
	 * The simple measure value to compare solutions performance to.
	 * 
	 * E.g. in Sudoku target is the 9x9 target matrix, but reference measure 
	 * is total rows, columns, squares completed (27)
	 * 
	 * @return
	 */
	public M getReferenceMeasure();
	public void setReferenceMeasure(M p);
	
	
	/**
	 * The target level of the goal, with respect to normalized fitness:
	 * 1.0 BEST
	 * 0.5 AVERAGE
	 * 0.0 WORST
	 * 
	 * The engine tries to reach exactly this value: the nearest solution wins.
	 */
	public BigDecimal getTargetFitness();
	public void setTargetFitness(BigDecimal level);
	

	/**
	 * The target threshold of the goal, with respect to normalized fitness.
	 * Reached this value, solution is the best match and engine stops its execution.
	 * 
	 */
	public BigDecimal getTargetThreshold();
	public void setTargetThreshold(BigDecimal level);
}
