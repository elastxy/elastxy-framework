package it.red.algen.engine.interfaces;

import it.red.algen.domain.interfaces.Solution;

@SuppressWarnings("rawtypes")
public interface Mutator<S extends Solution> {
	
	// TODOA: introduction of genoma
	public void setGenesFactory(GenesFactory genesFactory);

	/**
	 * Takes a solution as an input and applies mutator operator,
	 * returning the original solution mutate
	 * 
	 * @param solution
	 * @return
	 */
	public S mutate(S solution);
}
