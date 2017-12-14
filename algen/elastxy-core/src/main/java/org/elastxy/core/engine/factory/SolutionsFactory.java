package org.elastxy.core.engine.factory;

import java.util.List;

import org.elastxy.core.domain.experiment.Solution;

public interface SolutionsFactory<G> {

	/**
	 * Create a random Solution with random Alleles from Genoma.
	 * 
	 * E.g. '4 * 7'
	 * 
	 * Used in random option in PopulationFactory.
	 * 
	 * @return
	 */
	public Solution createRandom(G genoma);
	

	/**
	 * Create a default, always the same initial Solution (to be evolved by mutation).
	 * 
	 * E.g. '0 + 0'
	 * 
	 * Used in non-random option in PopulationFactory
	 * 
	 * @return
	 */
	public Solution createBaseModel(G genoma);
	
	

//	/**
//	 * Create a solution populated with predefined values.
//	 * 
//	 * Useful to create benchmark solutions, or as help to calculate target, 
//	 * boundaries, etc.
//	 * 
//	 * E.g. '-1000 * 1000'
//	 * 
//	 * @return
//	 */
//	public Solution createPredefined(G genoma, List<Object> alleleValues);
}
