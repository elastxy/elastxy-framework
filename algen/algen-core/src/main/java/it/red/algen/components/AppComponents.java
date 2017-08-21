package it.red.algen.components;

import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.engine.AlleleGenerator;
import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.Incubator;
import it.red.algen.engine.Mutator;
import it.red.algen.engine.Recombinator;
import it.red.algen.engine.Selector;
import it.red.algen.tracking.DefaultSolutionRenderer;
import it.red.algen.tracking.SolutionRenderer;

/**
 * Contains all components needed for algorithm to work.
 * 
 * It'a simple container, not a ServiceLocator in that
 * it's created at boot time by a builder, than used to supply
 * the finite set of component to the algorithm.
 * 
 * TODO: ApplicationInfo, taken from MexApplication...
 * 
 * @author red
 *
 */
public class AppComponents {

	// TODOA
//	public static final String GenomaProvider = 	"genomaProvider";
//	public static final String AlleleGenerator = 	"alleleGenerator";
//	public static final String EnvFactory = 		"envFactory";
//	public static final String SolutionsFactory = 	"solutionsFactory";
//	public static final String PopulationFactory = 	"populationFactory";
	
	public static final String Incubator = 			"incubator";
	public static final String FitnessCalculator = 	"fitnessCalculator";
	
	public static final String Selector = 			"selector";
	public static final String Mutator = 			"mutator";
	public static final String Recombinator = 		"recombinator";
	
	public static final String SolutionRenderer = 	"renderer";

	// TODOA
//	public GenomaProvider genomaProvider;
//	public AlleleGenerator alleleGenerator;
//	public EnvFactory envFactory;
//	public PopulationFactory populationFactory;
	
	public SolutionsFactory solutionsFactory;
	
	public Incubator incubator;
	public FitnessCalculator fitnessCalculator;
	
	public Selector selector;
	public Mutator mutator;
	public Recombinator recombinator;
	
	public SolutionRenderer solutionRenderer = new DefaultSolutionRenderer(); // TODOM: move default to another class..
}
