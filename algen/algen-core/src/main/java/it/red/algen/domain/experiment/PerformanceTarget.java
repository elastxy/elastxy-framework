package it.red.algen.domain.experiment;

import java.math.BigDecimal;

/**
 * A generic target class expressing a generic performance index as a goal
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
	public M measure;
	public BigDecimal level;

	
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
		return measure;
	}
	@Override
	public void setReferenceMeasure(M measure) {
		this.measure = measure;
	}
	
	
	@Override
	public BigDecimal getTargetFitness() {
		return level;
	}
	@Override
	public void setTargetFitness(BigDecimal level) {
		this.level = level;
	}
	
	public String toString(){
		return String.format("PerformanceTarget: goal %d with required level %.3f", goal, level);
	}
}
