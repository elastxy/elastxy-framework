package it.red.algen.engine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.Randomizer;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Fitness;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObservable;
import it.red.algen.tracking.EnvObserver;

public class Evolver implements EnvObservable {
	private static Logger logger = Logger.getLogger(Evolver.class.getName());

	// ALGORITHM PARAMETERS
    public AlgorithmContext context;
    private FitnessTester fitnessTester;
    private StopConditionVerifier stopVerifier;

    // WORKING DATA
    private Env env;

    // LISTENER
    private EnvObserver observer;
    
    
    /**
     * Select Selector based on bean name configured in AlgorithmContext 
     * @param context
     * @param env
     */
    public Evolver(AlgorithmContext context, Env env){
    	this.context = context;
    	this.env = env;
    	this.fitnessTester = new StandardFitnessTester(context.application.fitnessCalculator);
    	this.stopVerifier = new StopConditionVerifier(context.parameters.stopConditions);
    }
    
    
    public void subscribe(EnvObserver l){
        observer = l;
        fitnessTester.subscribe(l);
    }

    public ExperimentStats getStats(){
    	return EnvSupport.getStats(env);
    }
    

	/**
	 * ============================================================
	 * 		EVOLUTION
	 * ============================================================
	 * 
	 * Starts system life.
	 * 
     */
    public void evolve(){
    	
    	EnvSupport.startTime(env);
    	if(context.monitoringConfiguration.verbose) env.generationsHistory.add(env.currentGen);
        
        
    	// TEST FITNESS - initial gen
        fitnessTester.test(env.currentGen, env);
        int generationSize = env.currentGen.solutions.size();
        Fitness lastGenFitness = env.currentGen.bestMatch.getFitness();
        boolean endConditionFound = checkEndCondition(lastGenFitness);
        
        
    	// Loops until end condition rises
        while(!endConditionFound) {
        	
        	// SELECTION
        	Population nextGeneration = selection(generationSize);
        	if(context.monitoringConfiguration.verbose) env.generationsHistory.add(nextGeneration);
        	
        	// For uniform distribution selector skip genetic operators
        	if(!context.parameters.randomEvolution) {
        		applyGeneticOperators(generationSize, nextGeneration);
        	}
        	
            // TEST FITNESS - next gen
            fitnessTester.test(nextGeneration, env);

            // Assign new generation
            lastGenFitness = env.currentGen.bestMatch.getFitness();
            
            // CHECK END CONDITION
            endConditionFound = checkEndCondition(lastGenFitness);
            
            env.currentGen = nextGeneration;
            env.currentGenNumber++;
        }
        
        // END OF EXPERIMENT
    }


	private void applyGeneticOperators(int generationSize, Population nextGeneration) {
		
		// BEST MATCHES - extract
		// TODOM: reuse some best matches for sharing their genetic material
		List<Solution> bestMatches = BestMatchesSupport.extractBestMatches(nextGeneration, context.parameters.elitarism);

		// LOOP OVER NON-BEST SHUFFLED
        Collections.shuffle(nextGeneration.solutions);
		for(int s=0; s < generationSize-bestMatches.size(); s=s+2){
		    
			// EXTRACT PARENTS
			Solution[] parents = {nextGeneration.solutions.get(s), nextGeneration.solutions.get(s+1)};

		    
		    // RECOMBINATION
			List<Solution> sons = recombination(parents);
		    
		    
		    // MUTATION
		    mutation(sons);
		
		    // REPLACE PARENTS WITH SONS
		    nextGeneration.solutions.set(s, sons.get(0));
		    nextGeneration.solutions.set(s+1, sons.get(1));
		}

		// BEST MATCHES - reinsert
		for(Solution s : bestMatches){ nextGeneration.add(s); }
	}


    

	

	/**
	 * ============================================================
	 * 		CHECK END CONDITION
	 * ============================================================
	 */

	private boolean checkEndCondition(Fitness lastGenFitness) {
		boolean endConditionFound = false;
		
		// Check threshold
		if(context.parameters.stopConditions.targetThreshold != null &&
				env.currentGen.bestMatch.getFitness().overThreshold(context.parameters.stopConditions.targetThreshold)){
			endConditionFound = goalReached();
		}
		
		// Check stability of the fitness value
		if(!endConditionFound && context.parameters.elitarism){
		    if(env.currentGen.bestMatch.getFitness().sameOf(lastGenFitness)){
		    	env.totIdenticalFitnesses++;
		        if(stopVerifier.isStable(env.totIdenticalFitnesses)){
		        	EnvSupport.stopTime(env);
				    env.targetReached = true;
		        	endConditionFound = true;
		        	fireStableSolutionEvent();
		        }
		    }
		    else {
		    	env.totIdenticalFitnesses = 0; // reset if doesn't match
		    }
		}
		
		// Check goal reached
		if(!endConditionFound && env.currentGen.bestMatch.getFitness().fit()){
			endConditionFound = goalReached();
		}
		
		// Check time stop
		if(!endConditionFound && !stopVerifier.onTime(env.currentGenNumber, EnvSupport.getLifeTimeInMillis(env))){
			EnvSupport.stopTime(env);
			endConditionFound = true;
		    fireHistoryEndedEvent();
		}
		return endConditionFound;
	}


	private boolean goalReached() {
		boolean endConditionFound;
		EnvSupport.stopTime(env);
		env.targetReached = true;
		endConditionFound = true;
		fireGoalReachedEvent();
		return endConditionFound;
	}
	

	
	
	/**
	 * ============================================================
	 * 		OPERATORS
	 * ============================================================
	 */

	private Population selection(int generationSize) {
		Population nextGeneration = context.application.selector.select(env.currentGen, env.genoma);
		fireNewGenerationEvent(env.currentGen, nextGeneration);
		if(generationSize!=nextGeneration.solutions.size()){
			String msg = String.format("Selected generation size (%d) differs from last (%d)", nextGeneration.solutions.size(), generationSize);
			logger.severe(msg);
			throw new IllegalStateException(msg);
		}
		return nextGeneration;
	}

	
	private List<Solution> recombination(Solution[] parents) {
		List<Solution> sons;
		boolean crossover = Randomizer.nextDouble() < context.parameters.recombinationPerc;
		if(crossover) {
		    sons = context.application.recombinator.recombine(Arrays.asList(parents));
		    fireCrossoverEvent(parents[0], parents[1], sons);
		}
		else {
			sons = Arrays.asList(parents[0].copy(), parents[1].copy());            
		}
		return sons;
	}

	
	private void mutation(List<Solution> sons) {
		boolean mute0 = Randomizer.nextDouble() < context.parameters.mutationPerc;
		boolean mute1 = Randomizer.nextDouble() < context.parameters.mutationPerc;
		if(mute0) { 
		    Solution old = sons.get(0);
		    Solution niu = old.copy();
		    context.application.mutator.mutate(niu, env.genoma);
		    sons.set(0, niu);
		    fireMutationEvent(old, niu);
		}
		if(mute1) { 
		    Solution old = sons.get(1);
		    Solution niu = old.copy();
		    context.application.mutator.mutate(niu, env.genoma);
		    sons.set(1, niu);
		    fireMutationEvent(old, niu);
		}
	}
	
	
	
	
	/**
	 * ============================================================
	 * 		EVENTS
	 * ============================================================
	 */

    private void fireNewGenerationEvent(Population lastGen, Population newGen){
        observer.newGenerationEvent(env.currentGenNumber+1, lastGen, newGen);
    }

    private void fireCrossoverEvent(Solution father, Solution mother, List<Solution> sons){
    	observer.crossoverEvent(father, mother, sons);
    }
    
    private void fireMutationEvent(Solution orig, Solution mutated){
    	observer.mutationEvent(orig, mutated);
    }
    
    private void fireGoalReachedEvent(){
        observer.goalReachedEvent(this);
    }

    private void fireStableSolutionEvent(){
        observer.stableSolutionEvent(this);
    }
    
    private void fireHistoryEndedEvent(){
        observer.historyEndedEvent(this);
    }
    
    
    public String toString(){
    	return String.format("Evolver: current Env %s", env);
    }
    
}
