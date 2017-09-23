package it.red.algen.engine.operators;

import java.util.Arrays;
import java.util.List;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Gene;
import it.red.algen.domain.genetics.genotype.Strand;

public class StrandRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters algorithmParameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.algorithmParameters = parameters;
    }

    
    
    /**
     * I punti di ricombinazione possono essere all'operatore o al secondo operando
     * Two are the expected parents
     * 
     * TODOM: more than 2 parents management 
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents){
		
		// Define cut point
		// TODOM: define cut point from metadata!
		Strand genotype0 = (Strand)parents.get(0).getGenotype();
		
		Solution[] offsprings = null;
		
		// for each chromosome
		for(int c=0; c < genotype0.getNumberOfChromosomes(); c++){
			List<Gene> genes = genotype0.chromosomes.get(c).genes;
			
			// TODOM: remove following redundancies with ChromosomeRecombinator
			int crossoverPoint = Math.floorDiv(genes.size(), 2);
			
			// Define parents and children as initial clones of the parents
			Solution offspring0 = parents.get(0).copy();
			Solution offspring1 = parents.get(1).copy();
	        List<Gene> offspring0Genes = ((Strand)offspring0.getGenotype()).chromosomes.get(c).genes;
	        List<Gene> offspring1Genes = ((Strand)offspring1.getGenotype()).chromosomes.get(c).genes;
			
			// Reset Fitness values
			offspring0.setFitness(null);
			offspring1.setFitness(null);
			
			offsprings = new Solution[2];
			offsprings[0] = offspring0;
			offsprings[1] = offspring1;
			
			// Recombine redistributing genotype on two offsprings
			RecombinatorLogics.cutAndSwapSequence(offspring0Genes, offspring1Genes, crossoverPoint);
		}
		
		// Returns the array
        return Arrays.asList(offsprings);
    }
	

}
