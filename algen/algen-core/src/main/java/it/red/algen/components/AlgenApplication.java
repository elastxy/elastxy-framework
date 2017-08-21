package it.red.algen.components;

import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.Incubator;
import it.red.algen.engine.Mutator;
import it.red.algen.engine.Recombinator;
import it.red.algen.engine.Selector;

/**
 * Contains all metadata needed for building the application
 * components.
 * 
 * All components are functions with no state giving the behaviour
 * to the algorithm.
 * 
 * These functions are of an application specific types or standard types.
 * 
 * With application bootstrap, they provide metadata for building
 * new AppComponents which are cached and accessible by AppComponentLocator.
 * 
 * After, when a new execution is requested, a new AlgorithmContext is built at runtime, 
 * and those components are injected in the context together with all execution specific
 * parameters:
 * 
 * - AlgorithmParameters
 * - EngineConfigurations
 * - ApplicationSpecifics
 * 
 * @author red
 *
 */
public class AlgenApplication {
	
	public FitnessCalculator fitnessCalculator;
	public Incubator incubator;
	public Selector selector;
	public Mutator mutator;
	public Recombinator recombinator;

}
