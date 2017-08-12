package it.red.algen.engine;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.metadata.GeneMetadata;

/**
 * Generates a new Allele given metadata
 * @author red
 *
 */
public interface AlleleGenerator {
	
	/**
	 * Generate a random allele
	 * @param metadata
	 * @return
	 */
	public Allele generate(GeneMetadata metadata);
	
	/**
	 * Generate a new allele with specific value
	 * 
	 * TODO: in some cases is to be check the presence in metadata values
	 * @param metadata
	 * @return
	 */
	public Allele generate(GeneMetadata metadata, Object value);
}
