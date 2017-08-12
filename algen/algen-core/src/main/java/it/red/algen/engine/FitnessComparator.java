package it.red.algen.engine;

import java.math.BigDecimal;
import java.util.Comparator;

import it.red.algen.domain.experiment.Solution;

/**
 * Compares two solutions fitness by value, ordering in DESC order
 * 
 * If one is null, puts it at the end.
 * If both are null, the first is chosen
 * 
 * @author red
 */

@SuppressWarnings("rawtypes")
public class FitnessComparator implements Comparator<Solution> {

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
		else {
			result = fitness1.compareTo(fitness2);
		}
		
		// MINUS SIGN => reverse order, from higher to lower
		return -result;
	}

}
