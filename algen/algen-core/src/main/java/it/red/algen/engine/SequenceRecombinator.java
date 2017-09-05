package it.red.algen.engine;

import java.util.Arrays;
import java.util.List;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.SequenceGenotype;

public class SequenceRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters parameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.parameters = parameters;
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
		SequenceGenotype genotype0 = (SequenceGenotype)parents.get(0).getGenotype();
		List<Gene> genes = genotype0.genes;
		
		
		
		
		int crossoverPoint = Math.floorDiv(genes.size(), 2);
		
		// Define parents and children as initial clones of the parents
		Solution offspring0 = parents.get(0).copy();
        Solution offspring1 = parents.get(1).copy();
        List<Gene> offspring0Genes = ((SequenceGenotype)offspring0.getGenotype()).genes;
        List<Gene> offspring1Genes = ((SequenceGenotype)offspring1.getGenotype()).genes;
        
        // Reset Fitness values
        offspring0.setFitness(null);
        offspring1.setFitness(null);
        
		Solution[] offsprings = new Solution[2];
		offsprings[0] = offspring0;
		offsprings[1] = offspring1;
        
		
        // Recombine redistributing genotype on two offsprings
		RecombinatorLogics.cutAndSwapSequence(offspring0Genes, offspring1Genes, crossoverPoint);
		
		// Returns the array
        return Arrays.asList(offsprings);
    }
}
