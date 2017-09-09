package it.red.algen.domain.genetics;

import java.util.List;
import java.util.Map;

import it.red.algen.dataaccess.WorkingDataset;


/**
 * Maintains the registry of all genetic assets.
 * Allows SolutionFactory to retrieve Alleles for creating the Genotype.
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
 * 
 * After that, when building phenotype, Incubator can access only 
 * this genetic material.
 * 
 * @author red
 *
 */
public interface Genoma {

	
	/**
	 * Returns the working data set this Genoma is working on
	 * TODOA: treat as a separate and parallel concept
	 * @return
	 */
	public WorkingDataset getWorkingDataset();
	public void setWorkingDataset(WorkingDataset workingDataset);

	
	/**
	 * Returns ordered positions codes:
	 * - progressive int "X" for a sequence
	 * - progressive couple of int "X.Y" for a single strand
	 * - progressive triple of int "X.Y.Z" for a double strand
	 * @return
	 */
	public List<String> getPositions();
	
//	/**
//	 * Returns the number of chromosomes by strand
//	 * @return
//	 */
//	public int getNumberOfStrands();

	/**
	 * Returns the number of chromosomes by strand
	 * TODOM: number of strands
	 * @return
	 */
	public int getNumberOfChromosomes();

	/**
	 * Returns the number of chromosomes
	 * TODOM: number of strands
	 * @return
	 */
	public int getNumberOfGenes(int chromosome);

	/**
	 * Returns the size of available positions
	 * @return
	 */
	public int getPositionsSize();
	
	
	/**
	 * True: the set of possible Allele is finished and predefined.
	 * 
	 * When creating or mutating a genotype for a solution is consumed until
	 * no Allele is present (which it's a client side responsibility to ensure)
	 * TODOM: limit logics embedded in a Genoma implementation
	 * 
	 * @return
	 */
	public boolean isLimitedAllelesStrategy();

	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy);
	
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
	

	/**
	 * Generate a new list of random Alleles for every position
	 * 
	 * @param metadataCodes
	 * @return
	 */
	public List<Allele> getRandomAlleles();

}
