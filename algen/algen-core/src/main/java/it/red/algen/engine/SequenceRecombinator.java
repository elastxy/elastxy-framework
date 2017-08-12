package it.red.algen.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.SequenceGenotype;

public class SequenceRecombinator implements Recombinator<Solution> {
    private static Random RANDOMIZER = new Random();
    
    private OperatorsParameters parameters;
    
    
    public void setup(OperatorsParameters parameters) {
    	this.parameters = parameters;
    }

    
    
    /**
     * I punti di ricombinazione possono essere all'operatore o al secondo operando
     * Two are the expected parents
     * 
     * TODOB: more than 2 parents management 
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents){
		
		// Define cut point
		// TODOM: from metadata!
		SequenceGenotype genotype0 = (SequenceGenotype)parents.get(0).getGenotype();
		int crossoverPoint = Math.floorDiv(genotype0.genes.size(), 2);
		
		// Define parents and children as initial clones of the parents
		Solution offspring0 = parents.get(0).copy();
        Solution offspring1 = parents.get(1).copy();
		Solution[] offsprings = new Solution[2];
		offsprings[0] = offspring0;
		offsprings[1] = offspring1;
        
        // Recombine redistributing genotype on two offsprings
		cutAndSwapGenotypes(
				(SequenceGenotype)offspring0.getGenotype(), 
				(SequenceGenotype)offspring1.getGenotype(), 
				crossoverPoint);
		
		// Returns the array
        return Arrays.asList(offsprings);
    }
	

	private void cutAndSwapGenotypes(SequenceGenotype off0, SequenceGenotype off1, int crossoverPoint){
		List<Gene> new0 = new ArrayList<Gene>(off0.genes.subList(0, crossoverPoint));
		new0.addAll(off1.genes.subList(crossoverPoint, off1.genes.size()-1));
		
		List<Gene> new1 = new ArrayList<Gene>(off1.genes.subList(0, crossoverPoint));
		new1.addAll(off0.genes.subList(crossoverPoint, off0.genes.size()-1));
	}
}
