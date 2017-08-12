package it.red.algen.dataaccess;

import it.red.algen.metadata.Genoma;

/**
 * Collects all genoma information from a given source
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
	public Genoma collect();
}
