package it.red.algen.engine;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;

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
