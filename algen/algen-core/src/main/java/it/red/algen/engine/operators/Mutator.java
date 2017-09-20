package it.red.algen.engine.operators;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genoma;

@SuppressWarnings("rawtypes")
public interface Mutator<S extends Solution, G extends Genoma> {
	
	/**
	 * Takes a solution as an input and applies mutator operator,
	 * returning the original solution mutate
	 * 
	 * @param solution
	 * @return
	 */
	public S mutate(S solution, G genoma);
}
