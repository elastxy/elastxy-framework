package org.elastxy.core.engine.operators;

import java.util.Arrays;
import java.util.List;

import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.engine.core.Randomizer;

public class ChromosomeRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters algorithmParameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.algorithmParameters = parameters;
    }

    
    
    /**
     * Recombination of two Solution. Two are the expected parents.
     * 
     * TODOA-2: cut point for operating on minimum set of genes (from beginning or end of sequence)
     * TODOM-2: check performance of Recombination operator
     * TODOM-4: more than 2 parents management
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents, boolean preserveAlleles){
		
		// Define cut point
		// TODOM-1: define cut point from metadata!
		Chromosome genotype0 = (Chromosome)parents.get(0).getGenotype();
		
		int genesSize = genotype0.genes.size();
		Solution[] offsprings = RecombinatorLogics.generateOffsprings(parents, genesSize, null, preserveAlleles, algorithmParameters.crossoverPointRandom);
		
		// Returns the array
        return Arrays.asList(offsprings);
    }
	
}
