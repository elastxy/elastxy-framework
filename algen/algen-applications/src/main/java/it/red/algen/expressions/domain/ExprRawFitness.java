package it.red.algen.expressions.domain;

import it.red.algen.domain.RawFitness;

public class ExprRawFitness implements RawFitness {
	public int distance;
	
	public ExprRawFitness(int distance){
		this.distance = distance;
	}

}