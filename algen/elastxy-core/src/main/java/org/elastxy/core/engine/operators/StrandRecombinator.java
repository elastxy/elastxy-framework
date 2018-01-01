package org.elastxy.core.engine.operators;

import java.util.Arrays;
import java.util.List;

import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.engine.core.Randomizer;

public class StrandRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters algorithmParameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.algorithmParameters = parameters;
    }

    
    
    /**
     *  Recombination of two Solution. Two are the expected parents.
     * 
     * TODO1-2: check performance of Recombination operator
     * TODO3-4: more than 2 parents management
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents, boolean preserveAlleles){
		if(preserveAlleles){
			throw new UnsupportedOperationException("Not yet implemented: please set recombination perc to 0");
		}
		
		// Define cut point
		// TODO2-1: define cut point from metadata!
		Strand genotype0 = (Strand)parents.get(0).getGenotype();
		
		Solution[] offsprings = null;
		
		// for each chromosome
		int tot = genotype0.getNumberOfChromosomes();
		for(int c=0; c < tot; c++){
			int genesSize = genotype0.chromosomes.get(c).genes.size();
			offsprings = RecombinatorLogics.generateOffsprings(parents, genesSize, c, preserveAlleles, algorithmParameters.crossoverPointRandom);
		}
		
		// Returns the array
        return Arrays.asList(offsprings);
    }


}
