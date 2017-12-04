package it.red.algen.engine.operators;

import java.util.Arrays;
import java.util.List;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Gene;
import it.red.algen.domain.genetics.genotype.Strand;
import it.red.algen.engine.core.Randomizer;

public class StrandRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters algorithmParameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.algorithmParameters = parameters;
    }

    
    
    /**
     *  Recombination of two Solution. Two are the expected parents.
     *  
     * In case of preserving alleles, with cutpoint fixed to half,
     * all genes will be swapped, leading to poor performance.
     * TODOM by now it's not implemented
     * 
     * TODOM: more than 2 parents management 
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents, boolean preserveAlleles){
		if(preserveAlleles){
			throw new UnsupportedOperationException("Not yet implemented: please set recombination perc to 0");
		}
		
		// Define cut point
		// TODOM: define cut point from metadata!
		Strand genotype0 = (Strand)parents.get(0).getGenotype();
		
		Solution[] offsprings = null;
		
		// for each chromosome
		for(int c=0; c < genotype0.getNumberOfChromosomes(); c++){
			int genesSize = genotype0.chromosomes.get(c).genes.size();
			offsprings = RecombinatorLogics.generateOffsprings(parents, genesSize, c, preserveAlleles, algorithmParameters.crossoverPointRandom);
		}
		
		// Returns the array
        return Arrays.asList(offsprings);
    }


}
