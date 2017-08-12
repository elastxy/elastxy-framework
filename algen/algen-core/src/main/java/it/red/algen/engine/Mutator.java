package it.red.algen.engine;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.metadata.Genoma;

@SuppressWarnings("rawtypes")
public interface Mutator<S extends Solution> {
	
	public void setGenoma(Genoma genoma);

	/**
	 * Takes a solution as an input and applies mutator operator,
	 * returning the original solution mutate
	 * 
	 * @param solution
	 * @return
	 */
	public S mutate(S solution);
}
