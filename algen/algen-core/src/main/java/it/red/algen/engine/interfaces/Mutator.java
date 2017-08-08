package it.red.algen.engine.interfaces;

import it.red.algen.domain.interfaces.Solution;
import it.red.algen.engine.factories.GenesFactory;

@SuppressWarnings("rawtypes")
public interface Mutator<S extends Solution> {
	
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
