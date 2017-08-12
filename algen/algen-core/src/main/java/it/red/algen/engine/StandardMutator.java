package it.red.algen.engine;

import java.util.List;
import java.util.Random;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.metadata.Genoma;

/**
 * Standard mutator based on metadata
 * @author red
 *
 */
public class StandardMutator implements Mutator<Solution> {
    private static Random RANDOMIZER = new Random();
    
	private Genoma genoma;
	
	@Override
	public void setGenoma(Genoma genoma) {
		this.genoma = genoma;
	}

	@Override
	public Solution mutate(Solution solution) {
		
		// Reset fitness so that it must be recalculated
		solution.setFitness(null);
		
		// Replace an allele to another of genoma for the same position
		List<String> positions = solution.getGenotype().getPositions();
		String positionToMutate = positions.get(RANDOMIZER.nextInt(positions.size()));
		Allele newAllele = genoma.createRandomAllele(positionToMutate);
		solution.getGenotype().swap(positionToMutate, newAllele);
		return solution;
	}

}
