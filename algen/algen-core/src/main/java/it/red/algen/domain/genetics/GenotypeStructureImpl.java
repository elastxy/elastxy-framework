package it.red.algen.domain.genetics;

import java.util.List;


/**
 * Base implementation of GenotypeStructure.
 * 
 * @author red
 *
 */
public abstract class GenotypeStructureImpl implements GenotypeStructure {

	/**
	 * Number of positions.
	 */
	protected int positionsSize;
	
	/**
	 * Flat ordered positions.
	 */
	protected List<String> positions;

	/**
	 * Number of chromosomes: if 1 is a single chromosome, more is a Strand.
	 */
	protected int numberOfChromosomes;
	
	
	@Override
	public int getPositionsSize(){
		return positionsSize;
	}


	@Override
	public List<String> getPositions() {
		return positions;
	}


	@Override
	public int getNumberOfChromosomes() {
		return numberOfChromosomes;
	}


	@Override
	public abstract int getNumberOfGenes(int chromosome);

	
// TODOM: multistrand
//	@Override
//	public int getNumberOfStrands() {
//		return 0;
//	}
}
