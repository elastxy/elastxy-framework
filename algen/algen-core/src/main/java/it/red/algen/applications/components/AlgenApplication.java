package it.red.algen.applications.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contains all static metadata needed for building the application
 * components.
 * 
 * All components are functions with no state giving the behaviour
 * to the algorithm.
 * 
 * These functions are of an application specific types or standard types:
 * - fully qualified class name of the specific type
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
	public String name;

	public ComponentMetadata envFactory;

	public ComponentMetadata datasetProvider;
	public ComponentMetadata genomaProvider;
	public ComponentMetadata alleleGenerator;
	
	public ComponentMetadata populationFactory;
	public ComponentMetadata solutionsFactory;
	
	public ComponentMetadata fitnessCalculator;
	public ComponentMetadata incubator;
	
	public ComponentMetadata selector;
	public ComponentMetadata mutator;
	public ComponentMetadata recombinator;

	public ComponentMetadata solutionRenderer;

	// Distributed application
	public ComponentMetadata multiColonyEnvFactory;
	public ComponentMetadata distributedGenomaProvider;
	
	
	public String toString(){
		return name==null ? "Unknown" : name;
	}
}
