package it.red.algen.engine.operators;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Gene;

public class RecombinatorLogics {
	private static Logger logger = Logger.getLogger(RecombinatorLogics.class);

	/**
	 * Takes two List S1 and S2 as an input and cuts at a crossover point,
	 * so that S1 is splitted into S1.a, S1.b, and S2 is splitted into S2.a, S2.b.
	 * 
	 * Then swaps Genes: S1.a is joined to S2.b, and S1.b to S2.a.
	 * 
	 * Swap of Genes ends until crossover point EXCLUDED.
	 * If crossover point is at the beginning, nothing is done.
	 * If crossover point is at the end, only last Gene is swapped.
	 * 
	 * For example, let's recombine: (1 * 2 / 3) with (4 - 5 + 6).
	 * With crossover point 2 (third position):
	 * S1.a = 1 *
	 * S1.b = 2 / 3
	 * S2.a = 4 -
	 * S2.b = 5 + 6
	 * => (1 * 5 + 6), (4 - 2 / 3)
	 * 
	 * With crossover point 4 (last position):
	 * S1.a = 1 * 2 /
	 * S1.b = 3
	 * S2.a = 4 - 5 +
	 * S2.b = 6
	 * => (1 * 2 / 6), (4 - 5 + 3)
	 * 
	 * 
	 * @param off0genes
	 * @param off1genes
	 * @param crossoverPoint
	 */
	public static void cutAndSwapSequence(List<Gene> off0genes, List<Gene> off1genes, int crossoverPoint){
		int sequenceEnd = off0genes.size();

		for(int pos=0; pos < sequenceEnd; pos++){
			if(pos < crossoverPoint){
				Gene tmp = off0genes.get(pos);
				off0genes.set(pos, off1genes.get(pos));
				off1genes.set(pos, tmp);
			}
//			else {
//				Gene tmp = off1genes.get(pos);
//				off1genes.set(pos, off0genes.get(pos));
//				off0genes.set(pos, tmp);
//			}
		}
	}	
	
	
	
	public static List<Solution> recombineList(Recombinator recombinator, List<Solution> solutions, boolean preserveAlleles){
		logger.info("*** INPUT"+solutions);
		// LOOP OVER NON-BEST SHUFFLED
        Collections.shuffle(solutions);
        int solutionsSize = solutions.size();
		for(int s=0; s < solutionsSize && s != solutionsSize-1; s=s+2){
		    
			// EXTRACT PARENTS
			Solution[] parentsArray = {solutions.get(s), solutions.get(s+1)};

		    // RECOMBINATION
			List<Solution> parents = Arrays.asList(parentsArray);
			logger.info("*** RECOMBINING PARENTS "+parents);
			List<Solution> sons = recombinator.recombine(parents, preserveAlleles);
			logger.info("*** RECOMBINED SONS "+sons);
		    
		    // REPLACE PARENTS WITH SONS
		    solutions.set(s, sons.get(0));
		    solutions.set(s+1, sons.get(1));
		}
		logger.info("*** OUTPUT"+solutions);
		return solutions;
	}

}
