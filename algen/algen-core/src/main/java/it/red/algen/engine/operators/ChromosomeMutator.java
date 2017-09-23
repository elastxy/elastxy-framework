package it.red.algen.engine.operators;

import java.util.List;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genoma;

/**
 * Standard mutator based on metadata
 * @author red
 *
 */
public class ChromosomeMutator implements Mutator<Solution, Genoma> {
	

	@Override
	public Solution mutate(Solution solution, Genoma genoma) {
		
		// Reset fitness so that it must be recalculated
		solution.setFitness(null);
		
		// Replace an allele to another of genoma for the same position
		List<String> positions = solution.getGenotype().getPositions();
		MutatorLogics.mutate(solution, genoma, positions);
		return solution;
	}


}
