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
     * TODOA-2: cut point for operating on minimum set of genes (from beginning or end of sequence)
     * TODOM-2: check performance of Recombination operator
     * TODOM-4: more than 2 parents management
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents, boolean preserveAlleles){
		if(preserveAlleles){
			throw new UnsupportedOperationException("Not yet implemented: please set recombination perc to 0");
		}
		
		// Define cut point
		// TODOM-1: define cut point from metadata!
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
