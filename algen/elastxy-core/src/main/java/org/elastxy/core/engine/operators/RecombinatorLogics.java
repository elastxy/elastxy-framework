package org.elastxy.core.engine.operators;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.engine.core.Randomizer;

public class RecombinatorLogics {
	private static Logger logger = Logger.getLogger(RecombinatorLogics.class);


	public static Solution[] generateOffsprings(List<Solution> parents, int genesSize, Integer c, boolean preserveAlleles, boolean crossoverPointRandom) {
		Solution[] offsprings;
		int crossoverPointHalf = Math.floorDiv(genesSize, 2);
		int crossoverPoint = crossoverPointRandom ? Randomizer.nextInt(genesSize) : crossoverPointHalf;
		
		// Define parents and children as initial clones of the parents without fitness and phenotype info
		Solution offspring0 = parents.get(0).copyGenotype();
		Solution offspring1 = parents.get(1).copyGenotype();
		List<Gene> offspring0Genes = RecombinatorLogics.getGenes(offspring0, c);
		List<Gene> offspring1Genes = RecombinatorLogics.getGenes(offspring1, c);
		
		// Reset Fitness values
		offspring0.setFitness(null);
		offspring1.setFitness(null);
		
		offsprings = new Solution[2];
		offsprings[0] = offspring0;
		offsprings[1] = offspring1;

		// Recombine redistributing genotype on two offsprings
		if(!preserveAlleles){
			RecombinatorLogics.onePointCrossover(offspring0Genes, offspring1Genes, crossoverPoint);
		}
		// Recombine swapping genes until cutting point
		else {
			RecombinatorLogics.cycleCrossover(offspring0Genes, offspring1Genes, crossoverPoint);
		}
		return offsprings;
	}
	
	public static List<Gene> getGenes(Solution offspring, Integer chromosome){
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
	 * TODOA-2: specific Recombinator implementation: Default, CX
	 * 
	 * @param off0genes
	 * @param off1genes
	 * @param crossoverPoint
	 */
	public static void onePointCrossover(List<Gene> off0genes, List<Gene> off1genes, int crossoverPoint){
		for(int pos=0; pos < crossoverPoint; pos++){
			Gene tmp = off0genes.get(pos);
			off0genes.set(pos, off1genes.get(pos));
			off1genes.set(pos, tmp);
		}
	}	

	
	/**
	 * Takes two offsprings and swap alleles maintaining the same set
	 * of alleles on each offspring.
	 * 
	 * Example (middle pointcut):
	 * (1 * 2 / 3) with (2 / 3 * 1), cut point 2 => (1 *)+(2 /) to be swapped
	 * => Normal crossover: (2/|2/3) (1*|3*1) + [2:1,/:*] old values to be redistributed
	 * => Redistribution
	 * (2/|1/3) (1*|3*1)
	 * (2/|1/3) (1*|3*2)
	 * (2/|1*3) (1*|3*2)
	 * (2/|1*3) (1*|3/2)
	 * 
	 * 
	 * Example (last position):
	 * (1*2/3) with (2/3*1), cut point 4 => (3)+(1) to be swapped
	 * => Normal crossover: (2/3*|3)(1*2/|1)
	 * => Redistribution
	 * (2/3*|3) (1*2/|1)
	 * (2/3*|2) (1*2/|1)
	 * 
	 * TODOM-2: For efficiency, better swapping those in shorter part.
	 * 
	 * @param off0genes
	 * @param off1genes
	 * @param crossoverPoint
	 */
	public static void cycleCrossover(List<Gene> off0genes, List<Gene> off1genes, Integer crossoverPoint){

		List<Gene>[] offsprings = CXRecombinator.recombine(off0genes, off1genes, crossoverPoint);
		for(int i=0; i<offsprings[0].size(); i++) off0genes.set(i, offsprings[0].get(i)); 
		for(int i=0; i<offsprings[1].size(); i++) off1genes.set(i, offsprings[1].get(i)); 
		
	}
	
	
}
