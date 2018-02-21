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
    public static final int SCALE = 20;

	@Override
	public int compare(Solution a, Solution b) {
		// null are worst cases
		BigDecimal aFitness = a.getFitness()==null || a.getFitness().getValue()==null ? BigDecimal.ZERO : a.getFitness().getValue().setScale(SCALE, BigDecimal.ROUND_HALF_UP);;
		BigDecimal bFitness = b.getFitness()==null || b.getFitness().getValue()==null ? BigDecimal.ZERO : b.getFitness().getValue().setScale(SCALE, BigDecimal.ROUND_HALF_UP);;
		return bFitness.compareTo(aFitness);
	}

}
