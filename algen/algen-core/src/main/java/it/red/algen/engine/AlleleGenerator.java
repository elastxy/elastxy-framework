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
	public Allele generate(GeneMetadata metadata);
	
	/**
	 * Generate a new allele with specific value
	 * 
	 * TODO: in some cases is to be check the presence in metadata values
	 * @param metadata
	 * @return
	 */
	public Allele generate(GeneMetadata metadata, Object value);
	

	/*
	 * Generates a new allele, excluding some specific values.
	 * 
	 * Useful for problems where every gene must have a different allele from a predefined set
	 * 
	 */
	public Allele generateExclusive(GeneMetadata metadata, List<Object> exclusions);
}
