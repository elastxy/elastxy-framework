package it.red.algen.domain.experiment;

import java.math.BigDecimal;

/**
 * A target class where the goal to achieve is the nearest possible
 * to a given population.
 * 
 * @author red
 *
 */
public class SolutionTarget implements Target<Solution<?,?>,Object> {
	public Solution<?,?> goal;
	public Object measure;
	public BigDecimal level;
	public BigDecimal threshold;
	

	@Override
	public Solution<?,?> getGoal() {
		return goal;
	}
	public void setGoal(Solution<?,?> goal) {
		this.goal = goal;
	}
	

	@Override
	public Object getReferenceMeasure() {
		return measure;
	}
	public void setReferenceMeasure(Object measure) {
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

	
	@Override
	public BigDecimal getTargetThreshold() {
		return threshold;
	}
	@Override
	public void setTargetThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}
	
	public String toString(){
		return String.format("SolutionTarget: goal %s with required level %.3f", goal, level);
	}
}
