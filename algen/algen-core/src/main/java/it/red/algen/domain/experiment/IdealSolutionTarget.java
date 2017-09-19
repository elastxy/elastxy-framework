//package it.red.algen.domain.experiment;
//
//import java.math.BigDecimal;
//
///**
// * A target class where the goal to achieve is the nearest possible
// * to a given population.
// * 
// * @author red
// *
// */
//public class IdealSolutionTarget implements MultipleTarget<Solution<?,?>,Object> {
//	public Solution<?,?> goal;
//	public Object referenceMeasure;
//	public BigDecimal targetFitness;
//	public BigDecimal targetThreshold;
//
//	public Double[] weights;
//
//	@Override
//	public Solution<?,?> getGoal() {
//		return goal;
//	}
//	public void setGoal(Solution<?,?> goal) {
//		this.goal = goal;
//	}
//	
//
//	@Override
//	public Object getReferenceMeasure() {
//		return referenceMeasure;
//	}
//	public void setReferenceMeasure(Object measure) {
//		this.referenceMeasure = measure;
//	}
//	
//	
//	@Override
//	public BigDecimal getTargetFitness() {
//		return targetFitness;
//	}
//
//	@Override
//	public void setTargetFitness(BigDecimal level) {
//		if(level!=null && level.setScale(10).compareTo(BigDecimal.ONE.setScale(10))==0){
//			this.targetFitness = null;
//		}
//		else {
//			this.targetFitness = level;
//		}
//	}
//
//	
//	@Override
//	public BigDecimal getTargetThreshold() {
//		return targetThreshold;
//	}
//	@Override
//	public void setTargetThreshold(BigDecimal threshold) {
//		this.targetThreshold = threshold;
//	}
//
//	
//	@Override
//	public Double[] getWeights() {
//		return weights;
//	}
//	
//	@Override
//	public void setWeights(Double... weights) {
//		this.weights = weights;
//	}
//	
//	public String toString(){
//		return String.format("SolutionTarget: goal %s with required level %.3f", goal, targetFitness);
//	}
//	
//}
