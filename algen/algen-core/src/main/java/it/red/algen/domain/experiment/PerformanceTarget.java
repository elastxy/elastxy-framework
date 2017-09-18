package it.red.algen.domain.experiment;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	
	public Double[] weights;
	
	public BigDecimal level;
	public BigDecimal threshold;

	
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
		if(level!=null && level.setScale(10, RoundingMode.CEILING).compareTo(BigDecimal.ONE.setScale(10, RoundingMode.CEILING))==0){
			this.level = null;
		}
		else {
			this.level = level;
		}
	}
	
	
	@Override
	public BigDecimal getTargetThreshold() {
		return threshold;
	}
	@Override
	public void setTargetThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}
	
	
	public String toString(){
		return String.format("PerformanceTarget[goal=%d;level=%.3f,threshold=%.3f", goal, level, threshold);
	}
}
