package it.red.algen.dataaccess;

import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;

/**
 * Collects all genoma information from a given source.
 * 
 * Optionally, Genoma view can be restricted to adhere to 
 * execution goal, for reducing genotype size and better efficiency.
 * 
 * Context: APPLICATION
 * 
 * @author red
 *
 */
public interface GenomaProvider {
	
	/**
	 * Returns a reference to the Genoma previously generated.
	 * @return
	 */
	public Genoma getGenoma();
	
	/**
	 * Builds or retrieve the whole Genoma, caching if necessary 
	 * for following executions.
	 * @return
	 */
	public void collect();
	
	/**
	 * Reduce to the minimum set of information for building solutions
	 * during current execution.
	 * 
	 * Returns a new Genoma for current execution.
	 * 
	 * TODOA: call it "restrict"
	 * @param target
	 */
	public Genoma reduce(Target<?,?> target);
	
}
