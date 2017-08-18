package it.red.algen.engine;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Fitness;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

/**
 * Calculate the fitness value of a Solution phenotype or genotype with respect to a Target
 * 
 * Optionally, it can be set up with a sorrounding environment.
 * 
 * @author red
 *
 * @param <S>
 * @param <T>
 * @param <F>
 */
public interface FitnessCalculator<S extends Solution, T extends Target, F extends Fitness> {

	/** Assign an incubator for growing and evaluating the offsprings
	 * 
	 * If needed, the FitnessCalculator can be aware of the surrounding environment
	 * for the solution to grow.
	 * 
	 * @param incubator
	 */
	public void setup(Incubator<? extends Genotype, ? extends Phenotype> incubator, Env environment);
	

	/**
	 * Calculate Fitness value of a Solution given a Target
	 * @param solution
	 * @param target
	 * @return
	 */
    public F calculate(S solution, T target);
    
}
