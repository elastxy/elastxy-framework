package it.red.algen.domain.genetics;

import java.util.List;
import java.util.SortedMap;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;


/**
 * RESPONSIBILITIES
 * - Maintains the registry of all genetic assets created by Genoma Provider.
 * - Internally represents the structure of genotype produced by genoma provider
 *  (three types: single chromosome, strand, multiple strand).
 * - Selection support: builds Genotype using Genotype Structure as a blueprint, 
 *  using Metadata or Alleles predefined value, depending on implementation.
 * - Mutation support: mutate a given Genotype.
 * 
 * 
 * <b>Metadata</b>
 * 
 * For generic reusing, metadata could be managed depending on implementation,
 * to abstract from specific domain.
 * 
 * <b>Fixed and variable parts</b>
 * 
 * For incrementing efficiency, genoma must separate genetic materials
 * useful to solve the problem from those commons to all genes
 * and not giving an additional resource for solving the problem.
 * 
 * For example, it must include all valuable data for providing
 * a computable genotype of a solution, but not genetic information
 * fixed and constant for all solutions, even if useful for building 
 * a phenotype.
 * 
 * E.g. for MES, the fixed part of matrix could have been represented
 * by genes, but it would have took too much time and memory cost
 * to create and build solution with all 81 cells!
 * Instead, only the N free cells constitute the material useful to solve the problem.
 * 
 * E.g. Recipes complete objects are not pertinent to genetic algorithm,
 * so an access to WorkingDataset is needed.
 * 
 * Nonetheless, the whole genetic material is useful to complete the genotype
 * of a solution when building a phenotype, so it must be accessible
 * from a Incubator, beside the variable part.
 * 
 * 
 * <b>Goal based genoma</b>
 * 
 * Sometimes the solution variable part of genoma is driven by goal,
 * so that is linked to a specific execution.
 * 
 * For example, MES matrix presents free cells whose positions are based 
 * on every execution goal.
 * 
 * This can be managed by specifying a goal based Genoma, holding only
 * useful alleles relevant to solutions genotype for that goal.
 * This is accomplished with restrict function of Genoma Provider.
 * 
 * After that, when building phenotype, Incubator can access only 
 * this genetic material and must add the remaining.
 * 
 * @author red
 *
 */
public interface Genoma {

	
	/**
	 * Returns the Genotype structure.
	 * @return
	 */
	public GenotypeStructure getGenotypeStructure();
	
	/**
	 * Sets the structure.
	 * @param structure
	 */
	public void setGenotypeStructure(GenotypeStructure structure);
	
	
	/**
	 * True: the set of possible Allele values is limited and predefined.
	 * 
	 * When creating or mutating a genotype for a solution, possible values 
	 * are consumed until no Allele is present (which it's a client side 
	 * responsibility to ensure).
	 * 
	 * TODOM: limit logics embedded in a Genoma implementation
	 * 
	 * @return
	 */
	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy);

	
	

	/**
	 * Mutate given positions in the Solution, getting a new Allele 
	 * or swapping two existing, based on limited allele strategy.
	 * @param solution
	 * @param positions
	 */
	public void mutate(Solution solution, List<String> positions);
	
	
	
	/**
	 * Get all available alleles always in the same order.
	 * 
	 * Used to create a default initial population, for test purposes for example.
	 * 
	 * @return
	 */
	public List<Allele> getOrderedAlleles();
	

	/**
	 * Generate a new list of random Alleles for every position
	 * 
	 * @param metadataCodes
	 * @return
	 */
	public List<Allele> getRandomAlleles();

	/**
	 * The same as above but indexed by String position
	 * @return
	 */
	public SortedMap<String, Allele> getRandomAllelesAsMap();
	
	/**
	 * Retrieves a random Allele suitable for the given position in the sequence
	 * 
	 * This contract must be implemented for ALL Genoma types
	 * 
	 * @param position
	 * @return
	 */
	public Allele getRandomAllele(String position);
	

	/**
	 * Generates a list of random new Alleles, given the positions requested
	 * 
	 * @param position
	 * @return
	 */
	public List<Allele> getRandomAlleles(List<String> position);
	

}
