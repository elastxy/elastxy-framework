package it.red.algen.engine;

import it.red.algen.domain.experiment.Fitness;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.experiment.Target;

/**
 * Calculate the fitness value of a Solution phenotype or genotype with respect to a Target
 * @author red
 *
 * @param <S>
 * @param <T>
 * @param <F>
 * @param <P>
 */
public interface FitnessCalculator<S extends Solution, T extends Target, F extends Fitness> {

	/** Assign an incubator for growing and evaluating the offsprings
	 * 
	 * @param incubator
	 */
	public void setup(Incubator<?,?> incubator);
	
	/**
	 * Calculate Fitness value of a Solution given a Target
	 * @param solution
	 * @param target
	 * @return
	 */
    public F calculate(S solution, T target);
    
}
