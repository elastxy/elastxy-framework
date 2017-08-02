package it.red.algen.engine;

import it.red.algen.domain.Solution;

public interface SolutionsFactory {

	/**
	 * Create a random solution with random genes from genoma
	 * @return
	 */
	public Solution createRandom();
	

	/**
	 * Create a default, fixed initial solution to be evolved by mutation
	 * @return
	 */
	public Solution createBaseModel();
	
}
