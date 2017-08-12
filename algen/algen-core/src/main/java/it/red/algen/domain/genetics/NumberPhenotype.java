package it.red.algen.domain.genetics;

public class NumberPhenotype implements Phenotype<Number> {
	public Number value;
	
	@Override
	public Number getValue() {
		return value;
	}
	
	
	public NumberPhenotype copy(){
		NumberPhenotype result = new NumberPhenotype();
		result.value = value; // TODOM check
		return result;
	}
}
