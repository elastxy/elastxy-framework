package it.red.algen.domain.genetics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.AlgorithmException;

public class ChromosomeGenotypeStructure extends GenotypeStructureImpl {
	
	/**
	 * Builds the structure information.
	 * @param alleles
	 */
	public void build(Map<String, List<Allele>> alleles){
		positionsSize = alleles.size();
		positions = IntStream.range(0, alleles.size()).boxed().map(i -> i.toString()).collect(Collectors.toList());
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
