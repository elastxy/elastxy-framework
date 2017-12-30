package org.elastxy.core.engine.fitness;

import java.math.BigDecimal;
import java.util.Comparator;

import org.elastxy.core.domain.experiment.Solution;

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
	public int compare(Solution a, Solution b) {
		// null are worst cases
		BigDecimal aFitness = a.getFitness()==null || a.getFitness().getValue()==null ? BigDecimal.ZERO : a.getFitness().getValue();
		BigDecimal bFitness = b.getFitness()==null || b.getFitness().getValue()==null ? BigDecimal.ZERO : b.getFitness().getValue();
		return bFitness.compareTo(aFitness);
	}

}
