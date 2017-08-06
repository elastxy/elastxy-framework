package it.red.algen.engine;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.Population;

public interface Selector {

	public void setup(OperatorsParameters algParameters);
    

	/**
	 * Selects the next generation from a current based on specific strategy
	 * 
	 * @param generation
	 * @return
	 */
	public Population select(Population actualGeneration);
	
}
