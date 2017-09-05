package it.red.algen.engine;

import java.util.List;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.ChromosomeGenotype;
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
		
		// Replace an allele to another for the same position for each chromosome
		ChromosomeGenotype genotype = (ChromosomeGenotype)solution.getGenotype();
		for(int c=0; c < genoma.getNumberOfChromosomes(); c++){
			List<String> positions = genotype.getPositions(c);
			MutatorLogics.mutate(solution, genoma, positions);
		}
		
		return solution;
	}

}
