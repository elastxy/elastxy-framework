package it.red.algen.engine.operators;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public interface Selector<G extends Genoma> {

	public void setup(AlgorithmContext context);

	/**
	 * Selects the next generation from a current based on specific strategy
	 * 
	 * @param generation
	 * @return
	 */
	public Population select(Population actualGeneration, G genoma);
	
}
