package org.elastxy.core.domain.experiment;

import java.util.ArrayList;
import java.util.List;

/**
 * A standard implementation of MultiObjectiveTarget
 * @author red
 *
 * @param <G>
 * @param <M>
 */
public class MultiplePerformanceTarget<G, M> extends PerformanceTarget<G, M> implements MultipleTarget<G, M>{
	public TargetType targetType;
	public List<Target> targetList = new ArrayList<Target>();
	public Boolean[] neededTargetList; // default: null
	public Double[] weights; // default: null




	@Override
	public TargetType getTargetType() {
		return targetType;
	}

	@Override
	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
	
	
	@Override
	public List<Target> getTargetList() {
		return targetList;
	}

	@Override
	public void setTargetList(List<Target> targets) {
		this.targetList = targets;
	}
	
	@Override
	public Double[] getWeights() {
		return weights;
	}
	
	
	/**
	 * If not set, every target contributes for the same proportion.
	 */
	@Override
	public void setWeights(Double... weights) {
		this.weights = weights;
	}

	@Override
	public Boolean[] getNeededTargetList() {
		return neededTargetList;
	}

	@Override
	public void setNeededTargetList(Boolean... needed) {
		this.neededTargetList = needed;
	}

	
	public String toString(){
		return String.format("MultiplePerformanceTarget[type=%s,targetFitness=%.3f,threshold=%.3f,weights=%s,targetsNumber=%s", targetType, targetFitness, targetThreshold, weights, targetList);
	}
}
