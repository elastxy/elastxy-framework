package it.red.algen.engine.fitness;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Fitness;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.phenotype.Phenotype;

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
