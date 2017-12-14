package org.elastxy.core.engine.operators;

import java.util.List;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;

/**
 * Standard mutator based on metadata
 * 
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
		genoma.mutate(solution, positions);
		return solution;
	}


}
