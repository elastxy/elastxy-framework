package it.red.algen.dataaccess;

import java.util.List;

import it.red.algen.domain.experiment.Solution;

public interface SolutionsFactory {

	/**
	 * Create a random solution with random genes from genoma
	 * 
	 * E.g. '4 * 7'
	 * 
	 * @return
	 */
	public Solution createRandom();
	

	/**
	 * Create a default, fixed initial solution to be evolved by mutation
	 * 
	 * E.g. '0 + 0'
	 * 
	 * @return
	 */
	public Solution createBaseModel();
	
	

	/**
	 * Create a solution populated with predefined values
	 * (useful to create benchmark solutions, or as help to calculate target, boundaries...)
	 * 
	 * E.g. '-1000 * 1000'
	 * 
	 * @return
	 */
	public Solution createPredefined(List<Object> alleleValues);
}
