package it.red.algen.engine;

import java.util.Arrays;
import java.util.List;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.SequenceGenotype;

public class SequenceRecombinator implements Recombinator<Solution> {
    private OperatorsParameters parameters;
    
    
    public void setup(OperatorsParameters parameters) {
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
		int crossoverPoint = Math.floorDiv(genotype0.genes.size(), 2);
		
		// Define parents and children as initial clones of the parents
		Solution offspring0 = parents.get(0).copy();
        Solution offspring1 = parents.get(1).copy();
        
        // Reset Fitness values
        offspring0.setFitness(null);
        offspring1.setFitness(null);
        
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
		int sequenceEnd = off0.genes.size();

		for(int pos=0; pos < sequenceEnd; pos++){
			if(pos < crossoverPoint){
				Gene tmp = off0.genes.get(pos);
				off0.genes.set(pos, off1.genes.get(pos));
				off1.genes.set(pos, tmp);
			}
			else {
				Gene tmp = off1.genes.get(pos);
				off1.genes.set(pos, off0.genes.get(pos));
				off0.genes.set(pos, tmp);
			}
		}
	}
}
