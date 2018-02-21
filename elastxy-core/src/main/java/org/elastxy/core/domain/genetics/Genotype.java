package org.elastxy.core.domain.genetics;

import java.io.Serializable;
import java.util.List;

import org.elastxy.core.domain.genetics.genotype.Allele;


/**
 * Represents the genetic material of the individual solution.
 * @author red
 *
 */
public interface Genotype extends Serializable {
	
	
	/**
	 * Returns the list of all available positions
	 * @return
	 */
	public List<String> getPositions();

	
	/**
	 * Replaces: mutate an allele with another in the same position
	 */
	public void replaceAllele(String position, Allele allele);
	

	/**
	 * Swaps: change position of a given allele with another at sibling position
	 */
	public void swapAllele(String position, Allele allele);

	
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
