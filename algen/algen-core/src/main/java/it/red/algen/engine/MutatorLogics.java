package it.red.algen.engine;

import java.util.List;

import it.red.algen.context.Randomizer;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.Genoma;

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
	
	
}