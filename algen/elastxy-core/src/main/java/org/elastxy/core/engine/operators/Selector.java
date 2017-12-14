package org.elastxy.core.engine.operators;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.genetics.Genoma;

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
