package it.red.algen.engine;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Fitness;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

/**
 * Calculate the fitness value of a Solution phenotype or genotype with respect to n Environment
 * 
 * Optionally, it can be set up with a sorrounding environment.
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
	 * If needed, the FitnessCalculator can be aware of the surrounding environment
	 * for the solution to grow.
	 * 
	 * @param incubator
	 */
	public void setup(Incubator<? extends Genotype, ? extends Phenotype> incubator, Env environment);
	

	/**
	 * Calculate Fitness value of a Solution given an Environment (Target and sorroundings conditions)
	 * @param solution
	 * @param target
	 * @return
	 */
    public F calculate(S solution, Env environment);
    
}
