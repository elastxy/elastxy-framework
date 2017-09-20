package it.red.algen.domain.genetics;

import java.util.List;

import it.red.algen.engine.AlgorithmException;

public abstract class GenotypeStructureImpl implements GenotypeStructure {
	protected int positionsSize;
	protected List<String> positions;

	/**
	 * number of chromosomes: 1 is a sequence
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


//	@Override
//	public int getNumberOfStrands() {
//		return 0;
//	}


	
}
