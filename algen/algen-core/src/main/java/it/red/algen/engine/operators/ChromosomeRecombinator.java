package it.red.algen.engine.operators;

import java.util.Arrays;
import java.util.List;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.genotype.Gene;
import it.red.algen.domain.genetics.genotype.Strand;
import it.red.algen.engine.core.Randomizer;

public class ChromosomeRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters algorithmParameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.algorithmParameters = parameters;
    }

    
    
    /**
     * Recombination of two Solution. Two are the expected parents.
     * 
     * In case of preserving alleles, with cutpoint fixed to half,
     * all genes will be swapped, leading to poor performance.
     * TODOM by now it's not implemented
     * 
     * TODOM: more than 2 parents management
     * TODOA-2: cut point for operating on minimum set of genes
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents, boolean preserveAlleles){
		
		// Define cut point
		// TODOM: define cut point from metadata!
		Chromosome genotype0 = (Chromosome)parents.get(0).getGenotype();
		
		int genesSize = genotype0.genes.size();
		Solution[] offsprings = RecombinatorLogics.generateOffsprings(parents, genesSize, null, preserveAlleles, algorithmParameters.crossoverPointRandom);
		
		// Returns the array
        return Arrays.asList(offsprings);
    }
	
}
