package org.elastxy.core.engine.fitness;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.elastxy.core.engine.core.MathUtils;

public class FitnessUtils {

	/**
	 * Approximates fitness to nearest extreme value (ZERO, ONE) if needed.
	 * 
	 * @param fitness
	 * @return
	 */
	public static BigDecimal approximateFitness(BigDecimal fitness){
		BigDecimal result = fitness;
		if(fitness!=null){
			if(MathUtils.equals(fitness, BigDecimal.ONE, RoundingMode.CEILING)) {
				result = null; // algorithm tries to reach the maximum value and doesn't check exactly ONE (too expensive)
			}
			else if(MathUtils.equals(fitness, BigDecimal.ZERO, RoundingMode.FLOOR)) {
				result = BigDecimal.ZERO;
			}
		}
		return result;
	}
}
