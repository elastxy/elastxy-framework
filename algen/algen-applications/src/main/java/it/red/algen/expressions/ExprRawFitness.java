package it.red.algen.expressions;

import it.red.algen.RawFitness;

public class ExprRawFitness implements RawFitness {
	public int distance;
	
	public ExprRawFitness(int distance){
		this.distance = distance;
	}

}
