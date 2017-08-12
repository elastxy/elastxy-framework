package it.red.algen.domain.genetics;

import java.util.List;

public interface Genotype {
	
	
	/**
	 * Returns the list of all available positions
	 * TODOA: cache
	 * @return
	 */
	public List<String> getPositions();

	
	/**
	 * Swaps (mutate an allele with another for the same position)
	 */
	public void swap(String position, Allele allele);
	
	
	/**
	 * Encode the genotype in a single String representing the dominant alleles
	 * 
	 * Chromosomes are separated by a '.' character
	 * 
	 * E.g. [-132.+.87]
	 *  
	 * @return
	 */
	public String encode();
	
	
	public Genotype copy();
}
