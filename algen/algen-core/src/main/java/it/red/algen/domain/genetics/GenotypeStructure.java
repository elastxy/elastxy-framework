package it.red.algen.domain.genetics;

import java.util.List;

/**
 * Represents the structure of a Genotype produced by the Genoma.
 * 
 * It's initialized after creating a Genoma and does not change anymore.
 * It can be used as a blueprint for creating Genotypes by Genoma.
 *  
 * @author red
 *
 */
public interface GenotypeStructure {

	/**
	 * Returns the size of available positions.
	 * In case of a strand, is the sum of positions of all chromosomes.
	 * @return
	 */
	public int getPositionsSize();
	
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
	 * Returns the number of genes for given chromosome
	 * TODOM: number of strands
	 * @return
	 */
	public int getNumberOfGenes(int chromosome);
	
}
