package it.red.algen.engine;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.Population;
import it.red.algen.engine.interfaces.PopulationFactory;

public interface Selector {

	public void setup(OperatorsParameters algParameters);
    
	public void setup(OperatorsParameters algParameters, PopulationFactory populationFactory);

	/**
	 * Selects the next generation from a current based on specific strategy
	 * 
	 * @param generation
	 * @return
	 */
	public Population select(Population actualGeneration);
	
}
