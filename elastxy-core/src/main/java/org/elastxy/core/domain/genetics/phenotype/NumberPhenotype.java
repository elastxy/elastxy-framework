package org.elastxy.core.domain.genetics.phenotype;

public class NumberPhenotype implements Phenotype<Number> {
	public Number value;
	
	@Override
	public Number getValue() {
		return value;
	}
	
	
	public NumberPhenotype copy(){
		NumberPhenotype result = new NumberPhenotype();
		result.value = value;  // TODO1-4: shallow or deep copy? this is ok only if read only
		return result;
	}


	@Override
	public String toString() {
		return String.format("(NumberPhenotype) %s", value==null?"N/A":value.toString());
	}
}
