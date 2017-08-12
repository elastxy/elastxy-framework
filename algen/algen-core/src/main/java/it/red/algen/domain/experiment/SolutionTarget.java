package it.red.algen.domain.experiment;

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
	
	public Solution<?,?> getGoal() {
		return goal;
	}
	public void setGoal(Solution<?,?> goal) {
		this.goal = goal;
	}
	
	public Object getMeasure() {
		return measure;
	}
	public void setMeasure(Object measure) {
		this.measure = measure;
	}
}
