package org.elastxy.core.engine.fitness;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.phenotype.Phenotype;

/**
 * Calculate the fitness value of a Solution phenotype or genotype 
 * with respect to an Environment
 * 
 * @author red
 *
 * @param <S>
 * @param <T>
 * @param <F>
 */
public interface FitnessCalculator<S extends Solution, F extends Fitness> {

	/** Assign an incubator for growing and evaluating the offsprings
	 * 
	 * @param incubator
	 */
	public void setup(Incubator<? extends Genotype, ? extends Phenotype> incubator);
	

	/**
	 * Calculate Fitness value of a Solution given an Environment (Target and sorroundings conditions)
	 * 
	 * If needed, the FitnessCalculator can be aware of the surrounding environment
	 * for the solution to grow.
	 * 
	 * @param solution
	 * @param target
	 * @return
	 */
    public F calculate(S solution, Env environment);
    
}
