package it.red.algen.engine.fitness;

import java.math.BigDecimal;
import java.util.Comparator;

import it.red.algen.domain.experiment.Solution;

/**
 * Compares two solutions fitness by absolute proximity to the given fitness target, ordering in ASC order
 * 
 * If one is null, puts it at the end.
 * If both are null, the first is chosen
 * 
 * @author red
 */

@SuppressWarnings("rawtypes")
public class TargetFitnessComparator implements Comparator<Solution> {
	private BigDecimal targetFitness;
	
	public TargetFitnessComparator(BigDecimal targetFitness){
		this.targetFitness = targetFitness;
	}
	
	
	@Override
	public int compare(Solution arg1, Solution arg2) {
		BigDecimal fitness1 = arg1.getFitness()==null ? null : arg1.getFitness().getValue();
		BigDecimal fitness2 = arg2.getFitness()==null ? null : arg2.getFitness().getValue();
		int result = 0;
		if(fitness1==null && fitness2!=null){
			result = 1;
		}
		else if(fitness1!=null && fitness2==null){
			result = -1;
		}
		else if(fitness1==null && fitness2==null){
			result = 0;
		}
		else if(fitness1.compareTo(fitness2)==0){
			result = 0;
		}
		else {
			result = arg1.getFitness().nearestThan(arg2.getFitness(), targetFitness) ? 2 : -2; 
		}
		
		// MINUS SIGN => reverse order, from higher to lower
		return -result;
	}

}
