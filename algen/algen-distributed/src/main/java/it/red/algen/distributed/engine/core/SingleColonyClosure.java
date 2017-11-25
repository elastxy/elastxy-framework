package it.red.algen.distributed.engine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;

import it.red.algen.applications.components.AppComponents;
import it.red.algen.applications.components.AppComponentsLocator;
import it.red.algen.applications.components.factory.AppBootstrapRaw;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.stats.ExperimentStats;

/**
 * Takes one of the source data partition,
 * and uses that data for evolving a new population
 * using standard local algorithm.
 * 
 * For better efficiency, picks mutate genes from a broadcast
 * variable predetermined in colonies distributed evolver context
 * for every era iteration.
 * 
 * When result is found, long accumulator is incremented,
 * signalling to other colonies that one of the best solution is born.
 * 
 * @author red
 *
 */
public class SingleColonyClosure implements FlatMapFunction<Iterator<Allele>, Solution> {
	private static Logger logger = Logger.getLogger(SingleColonyClosure.class);
	
	private String applicationName = null;
	private long currentEraNumber = 0L;
	private AlgorithmContext context;
	private Target target;
    private LongAccumulator coloniesGoalAccumulator;
    private Broadcast<List<Allele>> mutatedGenesBC;
    
	public SingleColonyClosure(
			String applicationName,
			long currentEraNumber,
			AlgorithmContext context,
			Target target,
  			LongAccumulator coloniesGoalAccumulator,
  			Broadcast<List<Allele>> mutatedGenesBC){
		this.applicationName = applicationName;
		this.currentEraNumber = currentEraNumber;
		this.context = context;
		this.target = target;
		this.coloniesGoalAccumulator = coloniesGoalAccumulator;
		this.mutatedGenesBC = mutatedGenesBC;
	}
	
	
	public Iterator<Solution> call(Iterator<Allele> initialGenomaIterator) throws Exception {

		logger.info(">>> 2.0 Bootstrapping LOCAL application context");
		bootstrap();
		
		logger.info(String.format(">>> 2.1 Population Creation [era %d] 					WORKER => List[Solution]", currentEraNumber));
	    
		// Import Alleles from Iterator => new population
		List<Allele> newPopulationAlleles = new ArrayList<Allele>();
	    initialGenomaIterator.forEachRemaining(newPopulationAlleles::add);
	    
		// Import Alleles from Broadcast variable => new mutations
		List<Allele> mutationAlleles = mutatedGenesBC.getValue();

	    // TODOD: Creates a local complete Genoma Provider
	    
	    // Executes local Experiment
		SingleColonyClosureExperiment experiment = new SingleColonyClosureExperiment(
				context, 
				target, 
				newPopulationAlleles,
				mutationAlleles);
		experiment.run();
		ExperimentStats stats = experiment.getStats();
		List<Solution> bestMatches = new ArrayList<Solution>();
		bestMatches.add(stats.lastGeneration.bestMatch); // TODOD: a number of bestMatches
		return bestMatches.iterator();

		
//	    if(logger.isDebugEnabled()) {
//	    	val length = initialGenomaList.size
//	      logger.debug(s"    Starting from $length genes")
//	    }
	    
//	    
//	    var lastPopulation: MexPopulationE = FunctionsE.createInitialPopulation(numberOfSolutions, initialGenomaList)
//
//	    var stop:Boolean = false
//	    var generations = 1
//	    while(!stop) {
//	    
//	      val best = lastPopulation.bestMatch
//	      logger.info(s"----------->>> Generation $generations [era $era][Last best $best] <<<-----------")
//	      
//	      logger.info(">>> 2.2 Population Selection					WORKER => List[Solution]")
//	      // Elitarism: exclude last best match
//	    	var newPopulation:MexPopulationE = GeneticFunctionsE.selection(lastPopulation)
//	    	
//	      logger.info(">>> 2.3 Genetic Operators							WORKER => List[Solution]")
//	      if(mutatedGenesBC.isDefined){
//	        val mutatedGenoma = mutatedGenesBC.get.value
////	    	newPopulation = GeneticFunctions.recombination(newPopulation, numberOfSolutions, recombinationPerc)
//	    	  newPopulation = GeneticFunctionsE.mutation(newPopulation, mutatedGenoma, numberOfSolutions, mutationPerc)
//	      }
//	    
//	      logger.info(">>> 2.4 Fitness Calculation						WORKER => List[Solution] WORKER => List[Best]")
//	      // Elitarism: re-include last best match
//	      // TODOA: reinclude also in Era
//	      // TODOA: reinclude a percentage
//	      if(generations > 1) newPopulation = FunctionsE.reinsertBestMatch(lastPopulation, newPopulation)
//	      newPopulation = GeneticFunctionsE.testFitness(newPopulation, fitnessTolerance, target, maxOperandValue)
//	      
//	      logger.info(">>> 2.5 Check End Condition						WORKER => Accumulator")
//	      lastPopulation = newPopulation;
//	      if(newPopulation.goalReached){
//	        stop = true
//	        setColoniesGoal(newPopulation.goalReached, coloniesTargetStatus)
//	      }
//	      else if(generations >= maxGenerations || checkColoniesGoal(coloniesGoalAccumulator)){
//	        stop = true
//	      }
//	      
//	      val bestPrint = newPopulation.bestMatch
//	      logger.info(s"   End of generation [Gen: $generations][Last best $bestPrint][Stop: $stop]")
//	      generations += 1
//	    }
//	    
//	    var bestMatches = List(lastPopulation.bestMatch)
//	    bestMatches.iterator
	}


	private void bootstrap() {
		AppBootstrapRaw bootstrap = new AppBootstrapRaw();
		AppComponentsLocator locator = bootstrap.boot(applicationName);
		// TODOM: check if mandatory configurations are present!
		logger.info("Initializing LOCAL context.");
		setupContext(locator);
	}

	
	private void setupContext(AppComponentsLocator locator) {
		context.application = locator.get(applicationName);
		context.application.name = applicationName;
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context);
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.envFactory.setup(context);
	}
	
}