package org.elastxy.core.domain.genetics.phenotype;

public class NumberPhenotype implements Phenotype<Number> {
	public Number value;
	
	@Override
	public Number getValue() {
		return value;
	}
	
	
	public NumberPhenotype copy(){
		NumberPhenotype result = new NumberPhenotype();
		result.value = value;  // TODOA-4: create a clone? value by reference is ok only if read only
		return result;
	}


	@Override
	public String toString() {
		return String.format("(NumberPhenotype) %s", value==null?"N/A":value.toString());
	}
}
