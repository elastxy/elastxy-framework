package it.red.algen.engine;

import java.util.List;

import it.red.algen.context.Randomizer;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.Genoma;

/**
 * Standard mutator based on metadata
 * @author red
 *
 */
public class StandardMutator implements Mutator<Solution, Genoma> {
	

	@Override
	public Solution mutate(Solution solution, Genoma genoma) {
		
		// Reset fitness so that it must be recalculated
		solution.setFitness(null);
		
		// Replace an allele to another of genoma for the same position
		List<String> positions = solution.getGenotype().getPositions();
		String positionToMutate = positions.get(Randomizer.nextInt(positions.size()));
		
		if(genoma.isLimitedAllelesStrategy()){
			Allele newAllele = genoma.getRandomAllele(positionToMutate);
			solution.getGenotype().swapAllele(positionToMutate, newAllele);
		}
		else {
			Allele newAllele = genoma.getRandomAllele(positionToMutate);
			solution.getGenotype().replaceAllele(positionToMutate, newAllele);
		}
		return solution;
	}

}
