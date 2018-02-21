package org.elastxy.core.engine.operators;

import java.util.List;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Strand;

/**
 * Standard mutator based on metadata
 * @author red
 *
 */
public class StrandMutator implements Mutator<Solution, Genoma> {
	

	@Override
	public Solution mutate(Solution solution, Genoma genoma) {
		
		// Reset fitness so that it must be recalculated
		solution.setFitness(null);
		
		// Replace an allele to another for the same position for each chromosome
		Strand genotype = (Strand)solution.getGenotype();
		int tot = genoma.getGenotypeStructure().getNumberOfChromosomes();
		for(int c=0; c < tot; c++){
			List<String> positions = genotype.getPositions(c);
			genoma.mutate(solution, positions);
		}
		
		return solution;
	}

}
