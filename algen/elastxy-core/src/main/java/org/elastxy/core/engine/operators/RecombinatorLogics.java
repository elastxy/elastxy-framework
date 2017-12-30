package org.elastxy.core.engine.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.engine.core.Randomizer;
import org.elastxy.core.engine.operators.crossover.BinaryCrossover;
import org.elastxy.core.engine.operators.crossover.CXDCrossover;


/**
 * Support class for recombination logics.
 * 
 */
public class RecombinatorLogics {
	private static Logger logger = Logger.getLogger(RecombinatorLogics.class);


	/**
	 * Creates a couple of children individual based on parents.
	 * 
	 * IMPORTANT: parent are read-only, therefore any operations must be
	 * performes only on children.
	 * 
	 * @param parents - the two parents
	 * @param genesSize - genes length of a solution
	 * @param chromosome - chromosome number. Optional: needed for Chromosome or MultiStrand genotype.
	 * @param preserveAlleles
	 * @param crossoverPointRandom - if true, a random point is selected. If false, point is at the middle.
	 * @return
	 */
	public static Solution[] generateOffsprings(
			List<Solution> parents, 
			int genesSize, 
			Integer chromosome, 
			boolean preserveAlleles, 
			boolean crossoverPointRandom) {
		
		// Define parents and children as initial clones of the parents without fitness and phenotype info
		Solution[] offsprings = {
				parents.get(0).copyGenotype(),
				parents.get(1).copyGenotype()
		};
		
		// Extract and operates on initial genes of offsprings (clones of those of parents)
		List[] parentGenesCopy = {
				RecombinatorLogics.getGenes(offsprings[0], chromosome),
				RecombinatorLogics.getGenes(offsprings[1], chromosome)
		};
		
		List<Gene>[] offspringGenes = new List[2];
		
		// Recombine, redistributing genotype on two offsprings
		// TODO2-2: configurable injectable crossover implementation
		// TODO2-2: cut point for operating on minimum set of genes (from beginning or end of sequence)
		Integer crossoverPoint = crossoverPointRandom ? Randomizer.nextInt(genesSize) : Math.floorDiv(genesSize, 2);
		if(!preserveAlleles){
			offspringGenes = BinaryCrossover.recombine(parentGenesCopy[0], parentGenesCopy[1], crossoverPoint);
		}
		// Recombine swapping genes until cutting point
		else {
			offspringGenes = CXDCrossover.recombine(parentGenesCopy[0], parentGenesCopy[1], crossoverPoint);
		}
		
		// Set new genes lists to offsprings
		RecombinatorLogics.setGenes(offsprings[0], chromosome, offspringGenes[0]);
		RecombinatorLogics.setGenes(offsprings[1], chromosome, offspringGenes[1]);
		return offsprings;
	}
	
	private static List<Gene> getGenes(Solution offspring, Integer chromosome){
		List<Gene> result = null;
		Genotype genotype = offspring.getGenotype();
		if(genotype instanceof Chromosome){
			result = ((Chromosome)offspring.getGenotype()).genes;
		}
		else if(genotype instanceof Strand){
			result = ((Strand)offspring.getGenotype()).chromosomes.get(chromosome).genes;
		}
		return result;
	}

	
	private static void setGenes(Solution offspring, Integer chromosome, List<Gene> genes){
		Genotype genotype = offspring.getGenotype();
		if(genotype instanceof Chromosome){
			((Chromosome)offspring.getGenotype()).genes = genes;
		}
		else if(genotype instanceof Strand){
			((Strand)offspring.getGenotype()).chromosomes.get(chromosome).genes = genes;
		}
	}

	
	public static List<Solution> recombineList(Recombinator recombinator, List<Solution> solutions, boolean preserveAlleles){
		if(logger.isTraceEnabled()) logger.trace("*** INPUT"+solutions);
		// LOOP OVER NON-BEST SHUFFLED
        Collections.shuffle(solutions);
        int solutionsSize = solutions.size();
		for(int s=0; s < solutionsSize && s != solutionsSize-1; s=s+2){
		    
			// EXTRACT PARENTS
			Solution[] parentsArray = {solutions.get(s), solutions.get(s+1)};

		    // RECOMBINATION
			List<Solution> parents = Arrays.asList(parentsArray);
			if(logger.isTraceEnabled()) logger.trace("*** RECOMBINING PARENTS "+parents);
			List<Solution> sons = recombinator.recombine(parents, preserveAlleles);
			if(logger.isTraceEnabled()) logger.trace("*** RECOMBINED SONS "+sons);
		    
		    // REPLACE PARENTS WITH SONS
		    solutions.set(s, sons.get(0));
		    solutions.set(s+1, sons.get(1));
		}
		if(logger.isTraceEnabled()) logger.trace("*** OUTPUT"+solutions);
		return solutions;
	}
	
	
}
