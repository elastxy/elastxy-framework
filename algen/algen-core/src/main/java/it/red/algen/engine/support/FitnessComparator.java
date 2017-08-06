package it.red.algen.engine.support;

import java.util.Comparator;

import it.red.algen.domain.interfaces.Solution;

/**
 * Compares two solutions fitness by value, ordering in DESC order
 * 
 * If one is null, put at the end.
 * If both are null, the first is chose
 * 
 * @author red
 */

@SuppressWarnings("rawtypes")
public class FitnessComparator implements Comparator<Solution> {

	@Override
	public int compare(Solution arg1, Solution arg2) {
		Double fitness1 = arg1.getFitness()==null ? null : arg1.getFitness().getValue();
		Double fitness2 = arg2.getFitness()==null ? null : arg2.getFitness().getValue();
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
		else {
			result = fitness1.compareTo(fitness2);
		}
		
		// MINUS SIGN => reverse order, from higher to lower
		return -result;
	}

}
