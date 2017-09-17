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
	public <T> Allele<T> generate(GeneMetadata metadata);
	
	/**
	 * Generate a new allele with specific value
	 * 
	 * @param metadata
	 * @return
	 */
	public <T> Allele<T> generate(GeneMetadata metadata, T value);
	

	/*
	 * Generates a new allele, excluding some specific values.
	 * 
	 * Useful for problems where every gene must have a different allele from a predefined set
	 * 
	 */
	public <T> Allele<T> generateExclusive(GeneMetadata metadata, List<T> exclusions);
}
