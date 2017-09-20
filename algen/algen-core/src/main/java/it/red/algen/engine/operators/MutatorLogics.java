package it.red.algen.engine.operators;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.utils.Randomizer;

public class MutatorLogics {

	
	public static void mutate(Solution solution, Genoma genoma, List<String> positions) {
		// TODOM: remove redundancies, next rows are common to SequenceMutator
		String positionToMutate = positions.get(Randomizer.nextInt(positions.size()));
		if(genoma.isLimitedAllelesStrategy()){
			Allele newAllele = genoma.getRandomAllele(positionToMutate);
			solution.getGenotype().swapAllele(positionToMutate, newAllele);
		}
		else {
			Allele newAllele = genoma.getRandomAllele(positionToMutate);
			solution.getGenotype().replaceAllele(positionToMutate, newAllele);
		}
	}

	/**
	 * Swap positions of two alleles
	 * @param genes
	 * @param position
	 * @param newAllele
	 */
	public static void swapAllele(List<Gene> genes, String position, Allele newAllele) {
		
		// If the value is the same, leave it
		int newPosition = Integer.parseInt(position);
		if(genes.get(newPosition).allele.equals(newAllele)){
			return;
		}
		
		// Search for old position of the newAllele.. 
		OptionalInt oldPosition = IntStream.range(0, genes.size())
			     .filter(i -> newAllele.equals(genes.get(i).allele))
			     .findFirst();
		
		// New position is occupied by another allele..
		Allele otherAllele = genes.get(newPosition).allele;
		
		// That allele will replace new at its old position
		genes.get(oldPosition.getAsInt()).allele = otherAllele;
		genes.get(newPosition).allele = newAllele;
	}
	
	
}
