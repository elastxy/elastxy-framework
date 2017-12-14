package it.red.algen.engine.operators;

import java.util.List;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.domain.experiment.Solution;


/**
 * Recombinator operator
 * 
 * @author red
 * @param <R>
 */
@SuppressWarnings("rawtypes")
public interface Recombinator<R extends Solution> {

    public void setup(AlgorithmParameters algParameters);
    
    
    /**
     * Returns a list of offspring generated from a list of parents,
     * cross-cutting the genotype as configured by operator parameters.
     * 
     * If preserveAlleles is set, recombination preserves the (limited) set
     * of genes of both solutions.
     * 
     * @param parents
     * @return
     */
	public List<R> recombine(List<R> parents, boolean preserveAlleles);
	
}
