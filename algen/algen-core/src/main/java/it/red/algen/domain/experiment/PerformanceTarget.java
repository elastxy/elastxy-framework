package it.red.algen.domain.experiment;

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
	
	public G getGoal() {
		return goal;
	}
	public void setGoal(G goal) {
		this.goal = goal;
	}
	
	public M getMeasure() {
		return measure;
	}
	public void setMeasure(M measure) {
		this.measure = measure;
	}
}
