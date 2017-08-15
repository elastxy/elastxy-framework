package it.red.algen.domain.experiment;


/**
 * A simple raw fitness number value
 * @author red
 *
 */
public class NumberRawFitness implements RawFitness {
	public Number value;
	
	public NumberRawFitness(Number value){
		this.value = value;
	}
	
	public String toString(){
		return String.format("%.10f", value);
	}
}
