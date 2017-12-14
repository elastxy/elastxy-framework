package it.red.algen.domain.genetics;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.red.algen.engine.core.AlgorithmException;

public class ChromosomeGenotypeStructure extends GenotypeStructureImpl {

	/**
	 * Initialize structure by number of positions.
	 * 
	 * @param numberOfPositions
	 */
	public void build(int numberOfPositions){
		positionsSize = numberOfPositions;
		positions = IntStream.range(0, numberOfPositions).boxed().map(i -> i.toString()).collect(Collectors.toList());
		numberOfChromosomes = 1;
	}
	
	@Override
	public int getNumberOfGenes(int chromosome){
		if(chromosome!=0){
			throw new AlgorithmException("Chromosome cannot be more than 0 for a single chromosome structure. Chromosome requested: "+chromosome);
		}
		return positionsSize;
	}
	
}
