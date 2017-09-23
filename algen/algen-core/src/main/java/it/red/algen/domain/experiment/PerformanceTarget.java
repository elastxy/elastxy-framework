package it.red.algen.domain.experiment;

import java.math.BigDecimal;

import it.red.algen.engine.fitness.FitnessUtils;

/**
 * A generic target class expressing a generic performance index as a goal.
 * 
 * E.g. goal is the number to calculate, whereas measure is a measure of the distance 
 * to the interval upper limit
 * 
 * @author red
 * @param <G>
 *
 */
public class PerformanceTarget<G,M> implements Target<G,M> {
	public G goal;
	public M referenceMeasure;
	
	public BigDecimal targetFitness;
	public BigDecimal targetThreshold;

	
	@Override
	public G getGoal() {
		return goal;
	}
	@Override
	public void setGoal(G goal) {
		this.goal = goal;
	}

	
	@Override
	public M getReferenceMeasure() {
		return referenceMeasure;
	}
	@Override
	public void setReferenceMeasure(M measure) {
		this.referenceMeasure = measure;
	}
	


	@Override
	public BigDecimal getTargetFitness() {
		return targetFitness;
	}

	/**
	 * Sets target fitness, values in interval [0.0;1.0].
	 * 
	 * If value is near 1.0 or 0.0 at a big scale, is approximated.
	 * 
	 */
	@Override
	public void setTargetFitness(BigDecimal fitness) {
		this.targetFitness = FitnessUtils.approximateFitness(fitness);
	}
	
	
	@Override
	public BigDecimal getTargetThreshold() {
		return targetThreshold;
	}
	@Override
	public void setTargetThreshold(BigDecimal threshold) {
		this.targetThreshold = threshold;
	}
	
	
	public String toString(){
		return String.format("PerformanceTarget[goal=%d;targetFitness=%.3f,threshold=%.3f", goal, targetFitness, targetThreshold);
	}
}
