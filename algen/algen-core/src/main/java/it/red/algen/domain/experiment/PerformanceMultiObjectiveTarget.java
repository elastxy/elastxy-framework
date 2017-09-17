package it.red.algen.domain.experiment;


/**
 * A standard implementation of MultiObjectiveTarget
 * @author red
 *
 * @param <G>
 * @param <M>
 */
public class PerformanceMultiObjectiveTarget<G, M> extends PerformanceTarget<G, M> implements MultiObjectiveTarget<G, M>{

	public Double[] weights;

	
	@Override
	public Double[] getWeights() {
		return weights;
	}
	
	@Override
	public void setWeights(Double... weights) {
		this.weights = weights;
	}

	public String toString(){
		return String.format("PerformanceMultiObjectiveTarget[goal=%d;level=%.3f,threshold=%.3f,weights=%s", goal, level, threshold, weights);
	}
}
