package it.red.algen.engine.operators;

import java.util.List;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.genotype.Strand;

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
		Strand genotype = (Strand)solution.getGenotype();
		for(int c=0; c < genoma.getGenotypeStructure().getNumberOfChromosomes(); c++){
			List<String> positions = genotype.getPositions(c);
			MutatorLogics.mutate(solution, genoma, positions);
		}
		
		return solution;
	}

}
