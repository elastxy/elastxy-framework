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
	public BigDecimal getLevel() {
		return level;
	}
	@Override
	public void setLevel(BigDecimal level) {
		this.level = level;
	}
}
