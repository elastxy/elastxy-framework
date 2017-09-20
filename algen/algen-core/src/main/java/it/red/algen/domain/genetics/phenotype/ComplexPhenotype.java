package it.red.algen.domain.genetics.phenotype;

import java.util.HashMap;
import java.util.Map;

/**
 * A complex phenotype hosting arbitrary data
 * @author red
 *
 */
public class ComplexPhenotype implements Phenotype<Map<String,Object>> {
	public Map<String,Object> value = new HashMap<String,Object>();
	
	@Override
	public Map<String,Object> getValue() {
		return value;
	}
	
	
	public Phenotype<Map<String,Object>> copy(){
		ComplexPhenotype result = new ComplexPhenotype();
		result.value = value; // TODOM create a clone? by reference is ok only if read only
		return result;
	}


	@Override
	public String toString() {
		return String.format("ComplexPhenotype [value=%s]", value==null?"N/A":value.toString());
	}

}
