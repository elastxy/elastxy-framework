package it.red.algen.expressions.domain;

import it.red.algen.domain.RawFitness;

/**
 * Absolute distance from target value
 * @author red
 *
 */
public class ExprRawFitness implements RawFitness {
	public long distance;
	
	public ExprRawFitness(long distance){
		this.distance = distance;
	}

}
