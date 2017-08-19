package it.red.algen.dataaccess;

import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;

/**
 * Collects all genoma information from a given source.
 * 
 * Optionally, Genoma view can be restricted to adhere to 
 * execution goal, for reducing genotype size and better efficiency.
 * 
 * @author red
 *
 */
public interface GenomaProvider {
	
	/**
	 * Returns a reference to the Genoma previously generated
	 * @return
	 */
	public Genoma getGenoma();
	
	/**
	 * Builds or retrieve the whole Genoma
	 * @return
	 */
	public void collect();
	
	/**
	 * Reduce to the minimum set of information for building solutions
	 * during current execution
	 * @param target
	 */
	public Genoma reduce(Target<?,?> target);
}
