package org.elastxy.core.domain.experiment;


/**
 * A simple raw fitness number value
 * @author red
 *
 */
public class NumberRawFitness implements RawFitness {
	public Number value;
	
	public NumberRawFitness(){
	}
	
	public NumberRawFitness(Number value){
		this.value = value;
	}
	
	public String toString(){
		return value==null ? null : String.format("%.10f", value.floatValue());
	}
}
