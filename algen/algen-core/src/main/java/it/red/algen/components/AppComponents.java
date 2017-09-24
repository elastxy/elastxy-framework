package it.red.algen.components;

import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.dataaccess.DatasetProvider;
import it.red.algen.engine.fitness.FitnessCalculator;
import it.red.algen.engine.fitness.Incubator;
import it.red.algen.engine.metadata.AlleleGenerator;
import it.red.algen.engine.operators.Mutator;
import it.red.algen.engine.operators.Recombinator;
import it.red.algen.engine.operators.Selector;
import it.red.algen.tracking.SolutionRenderer;

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

	public static final String EnvFactory = 		"envFactory"; // Context-dependent
	
	public static final String DatasetProvider = 	"datasetProvider"; // Context-dependent
	public static final String GenomaProvider = 	"genomaProvider"; // Context-dependent
	public static final String AlleleGenerator = 	"alleleGenerator";
	
	public static final String PopulationFactory = 	"populationFactory";
	public static final String SolutionsFactory = 	"solutionsFactory";
	
	public static final String FitnessCalculator = 	"fitnessCalculator";
	public static final String Incubator = 			"incubator";
	
	public static final String Selector = 			"selector"; // Context-dependent
	public static final String Mutator = 			"mutator";
	public static final String Recombinator = 		"recombinator";
	
	public static final String SolutionRenderer = 	"renderer";


	public String name;
	
	public EnvFactory envFactory;

	public DatasetProvider datasetProvider;
	public GenomaProvider genomaProvider;
	public AlleleGenerator alleleGenerator;
	
	public PopulationFactory populationFactory;
	public SolutionsFactory solutionsFactory;
	
	public Incubator incubator;
	public FitnessCalculator fitnessCalculator;
	
	public Selector selector;
	public Mutator mutator;
	public Recombinator recombinator;
	
	public SolutionRenderer solutionRenderer;
	
	
	/**
	 * Creates a copy for redefining at runtime some behaviour
	 * @return
	 */
	public AppComponents copy(){
		AppComponents result = new AppComponents();
		
		result.name = name;
		
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
		return result;
	}
}
