package it.red.algen.metadata;

import java.util.List;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.engine.AlleleGenerator;


/**
 * Maintains the registry of all genetic assets.
 * 
 * @author red
 *
 */
public interface Genoma {

	
	/**
	 * Inject an allele generator implementation
	 * @param generator
	 */
	public void setupAlleleGenerator(AlleleGenerator generator);
	

	public boolean isLimitedAllelesStrategy();

	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy);
	
	/**
	 * Generates a random Allele given the position in the sequence
	 * 
	 * This contract must be implemented for ALL Genoma types
	 * 
	 * @param position
	 * @return
	 */
	public Allele createRandomAllele(String position);
	

	/**
	 * Generates a list of random new Alleles, given the positions requested
	 * @param position
	 * @return
	 */
	public List<Allele> createRandomAlleles(List<String> position);
	

	/**
	 * Generate a new list of random Alleles for every position
	 * @param metadataCodes
	 * @return
	 */
	public List<Allele> createRandomAlleles();

}
