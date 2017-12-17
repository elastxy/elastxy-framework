package org.elastxy.core.applications.components;

import java.io.Serializable;

import org.elastxy.core.dataprovider.DatasetProvider;
import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.engine.factory.EnvFactory;
import org.elastxy.core.engine.factory.PopulationFactory;
import org.elastxy.core.engine.factory.SolutionsFactory;
import org.elastxy.core.engine.factory.TargetBuilder;
import org.elastxy.core.engine.fitness.FitnessCalculator;
import org.elastxy.core.engine.fitness.Incubator;
import org.elastxy.core.engine.metadata.AlleleGenerator;
import org.elastxy.core.engine.operators.Mutator;
import org.elastxy.core.engine.operators.Recombinator;
import org.elastxy.core.engine.operators.Selector;
import org.elastxy.core.tracking.ResultsRenderer;
import org.elastxy.core.tracking.SolutionRenderer;

/**
 * Contains all components needed for algorithm to work.
 * 
 * It'a simple container, not a ServiceLocator in that
 * it's created at boot executionTimeMs by a builder, than used to supply
 * the finite set of component to the algorithm.
 * 
 * @author red
 *
 */
public class AppComponents implements Serializable {

//	public static final String ApplicationName = 	"appName";
//	public static final String ApplicationFolder = 	"appFolder";
//	
//	public static final String TargetBuilder = 		"targetBuilder"; // Context-dependent
//	public static final String EnvFactory = 		"envFactory"; // Context-dependent
//	
//	public static final String DatasetProvider = 	"datasetProvider"; // Context-dependent
//	public static final String GenomaProvider = 	"genomaProvider"; // Context-dependent
//	public static final String AlleleGenerator = 	"alleleGenerator";
//	
//	public static final String PopulationFactory = 	"populationFactory";
//	public static final String SolutionsFactory = 	"solutionsFactory";
//	
//	public static final String FitnessCalculator = 	"fitnessCalculator";
//	public static final String Incubator = 			"incubator";
//	
//	public static final String Selector = 			"selector"; // Context-dependent
//	public static final String Mutator = 			"mutator";
//	public static final String Recombinator = 		"recombinator";
//
//	public static final String SolutionRenderer = 	"renderer";
//	public static final String ResultsRenderer = 	"resultsRenderer";
//
//	public static final String MultiColonyEnvFactory =		"multiColonyEnvFactory"; // Context-dependent
//	public static final String DistributedDatasetProvider = "distributedDatasetProvider"; // Context-dependent
//	public static final String SingleColonyDatasetProvider ="singleColonyDatasetProvider"; // Context-dependent
//	public static final String DistributedGenomaProvider = 	"distributedGenomaProvider"; // Context-dependent

	
	public String appName;
	public String appFolder;
	
	public transient TargetBuilder targetBuilder;
	public transient EnvFactory envFactory;

	public transient DatasetProvider datasetProvider;
	public transient GenomaProvider genomaProvider;
	public transient AlleleGenerator alleleGenerator;
	
	public transient PopulationFactory populationFactory;
	public transient SolutionsFactory solutionsFactory;
	
	public transient Incubator incubator;
	public transient FitnessCalculator fitnessCalculator;
	
	public transient Selector selector;
	public transient Mutator mutator;
	public transient Recombinator recombinator;
	
	public transient SolutionRenderer solutionRenderer;
	public transient ResultsRenderer resultsRenderer;

	// Distributed application
	// TODOM-2: DistributedAppComponents extending this, with specific MultiColony- types?? another application.json extending local?
	public transient EnvFactory multiColonyEnvFactory;
	public transient DatasetProvider distributedDatasetProvider;
	public transient DatasetProvider singleColonyDatasetProvider;
	public transient GenomaProvider distributedGenomaProvider;
	
	/**
	 * Creates a copy for redefining at runtime some behaviour
	 * @return
	 */
	public AppComponents copy(){
		AppComponents result = new AppComponents();
		
		result.appName = appName;
		result.appFolder = appFolder;

		result.targetBuilder = targetBuilder;
		result.envFactory = envFactory;
		
		result.datasetProvider = datasetProvider;		
		result.genomaProvider = genomaProvider;
		result.alleleGenerator = alleleGenerator;
		
		result.populationFactory = populationFactory;
		result.solutionsFactory = solutionsFactory;
		
		result.incubator = incubator;
		result.fitnessCalculator = fitnessCalculator;
		
		result.selector = selector;
		result.mutator = mutator;
		result.recombinator = recombinator;
		
		result.solutionRenderer = solutionRenderer;
		result.resultsRenderer = resultsRenderer;

		// Distributed application
		result.multiColonyEnvFactory = multiColonyEnvFactory;
		result.distributedDatasetProvider = distributedDatasetProvider;
		result.singleColonyDatasetProvider = singleColonyDatasetProvider;
		result.distributedGenomaProvider = distributedGenomaProvider;
		
		return result;
	}
}
