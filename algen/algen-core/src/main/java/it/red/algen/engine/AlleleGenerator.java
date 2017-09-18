package it.red.algen.engine;

import java.util.List;

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
	public <T> Allele<T> generateRandom(GeneMetadata metadata);
	
	/**
	 * Generate a new allele with specific value
	 * 
	 * @param metadata
	 * @return
	 */
	public <T> Allele<T> generateFromValue(GeneMetadata metadata, T value);
	

	/*
	 * Generates a new allele, excluding some specific values.
	 * 
	 * Useful for problems where every gene must have a different allele from a predefined set
	 * 
	 */
	public <T> Allele<T> generateExclusive(GeneMetadata metadata, List<T> exclusions);
	

	/**
	 * Generate the Allele with the first value available in metadata values
	 * @param metadata
	 * @return
	 */
	public <T> Allele<T> generateFirst(GeneMetadata metadata);
}
