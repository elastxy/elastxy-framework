package it.red.algen.engine.fitness;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
			if(fitness.setScale(10, RoundingMode.CEILING).compareTo(BigDecimal.ONE.setScale(10, RoundingMode.CEILING))==0){
				result = null; // algorithm tries to reach the maximum value and doesn't check exactly ONE (too expensive)
			}
			else if(fitness.setScale(10, RoundingMode.FLOOR).compareTo(BigDecimal.ZERO.setScale(10, RoundingMode.FLOOR))==0){
				result = BigDecimal.ZERO;
			}
		}
		return result;
	}
}
