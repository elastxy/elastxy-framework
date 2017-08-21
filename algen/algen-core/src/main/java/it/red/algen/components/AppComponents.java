package it.red.algen.components;

import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.Incubator;
import it.red.algen.engine.Mutator;
import it.red.algen.engine.Recombinator;
import it.red.algen.engine.Selector;

/**
 * Contains all components needed for algorithm to work.
 * 
 * It'a simple container, not a ServiceLocator in that
 * it's created at boot time by a builder, than used to supply
 * the finite set of component to the algorithm.
 * 
 * @author red
 *
 */
public class AppComponents {
	
	public FitnessCalculator fitnessCalculator;
	public Incubator incubator;
	public Selector selector;
	public Mutator mutator;
	public Recombinator recombinator;
}
