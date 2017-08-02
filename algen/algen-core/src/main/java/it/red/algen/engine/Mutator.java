package it.red.algen.engine;

import it.red.algen.domain.Solution;

public interface Mutator {
	
	// TODOA: introductionof genoma
	public void setGenesFactory(GenesFactory genesFactory);

	/**
	 * Takes a solution as an input and applies mutator operator,
	 * returning the original solution mutate
	 * 
	 * @param solution
	 * @return
	 */
	public Solution mutate(Solution solution);
}
