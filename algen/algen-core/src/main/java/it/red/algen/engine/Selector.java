package it.red.algen.engine;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public interface Selector<G extends Genoma> {

	public void setup(AlgorithmParameters algParameters);
    
	public void setup(AlgorithmParameters algParameters, PopulationFactory populationFactory);

	/**
	 * Selects the next generation from a current based on specific strategy
	 * 
	 * @param generation
	 * @return
	 */
	public Population select(Population actualGeneration, G genoma);
	
}
