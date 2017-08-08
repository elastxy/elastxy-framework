package it.red.algen.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.Env;
import it.red.algen.domain.Population;
import it.red.algen.domain.interfaces.Fitness;
import it.red.algen.domain.interfaces.Solution;
import it.red.algen.engine.standard.StandardFitnessTester;
import it.red.algen.engine.support.BestMatchesUtil;
import it.red.algen.engine.support.EnvUtil;
import it.red.algen.engine.support.StopConditionVerifier;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObservable;
import it.red.algen.tracking.EnvObserver;

public class Evolver implements EnvObservable {
	private static Logger logger = Logger.getLogger(Evolver.class.getName());

    private static Random RANDOMIZER = new Random();
    
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
    	this.fitnessTester = new StandardFitnessTester(context.fitnessCalculator);
    	this.stopVerifier = new StopConditionVerifier(context.stopConditions);
    }
    
    
    public void subscribe(EnvObserver l){
        observer = l;
        fitnessTester.subscribe(l);
    }

    public ExperimentStats getStats(){
    	return EnvUtil.getStats(env);
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
    	
    	EnvUtil.startTime(env);
        
        
    	// TEST FITNESS - initial gen
        fitnessTester.test(env.target, env.currentGen);
        int generationSize = env.currentGen.solutions.size();
        // fireTestedGenerationEvent(); // TODOM
        
        
    	// Loops until end condition rises
        boolean endConditionFound = false;
        do {
        	
        	// SELECTION
        	Population nextGeneration = selection(generationSize);
        	
        	// BEST MATCH - calculate
        	List<Solution> bestMatches = BestMatchesUtil.extractBestMatches(nextGeneration, context.parameters.elitarism);

        	// LOOP OVER NON-BEST
        	for(int s=0; s < generationSize-bestMatches.size(); s=s+2){
                
        		// EXTRACT PARENTS
        		Solution[] parents = {nextGeneration.solutions.get(s), nextGeneration.solutions.get(s+1)};

                
                // RECOMBINATION
        		List<Solution> sons = recombination(parents);
                
                
                // MUTATION
                mutation(sons);
        	
                // REPLACE PARENTS WITH SONS
                nextGeneration.solutions.set(s, sons.get(0));
                nextGeneration.solutions.set(s, sons.get(1));
        	}

        	// BEST MATCH - reinsert
        	for(Solution s : bestMatches){ nextGeneration.add(s); }
        	
            // TEST FITNESS - next gen
            Fitness nextGenFitness = fitnessTester.test(env.target, nextGeneration);

            // CHECK END CONDITION
            endConditionFound = checkEndCondition(nextGenFitness);
            
            env.currentGen = nextGeneration;
            if(context.monitoringConfiguration.verbose) env.generationsHistory.add(env.currentGen);
            env.currentGenNumber++;
        }
        while(!endConditionFound);
        
        // END OF EXPERIMENT
        EnvUtil.stopTime(env);
    }


    

	

	/**
	 * ============================================================
	 * 		CHECK END CONDITION
	 * ============================================================
	 */

	private boolean checkEndCondition(Fitness nextGenFitness) {
		boolean endConditionFound = false;
		
		// Check stability of the fitness value
		if(context.parameters.elitarism){
		    if(env.currentGen.bestMatch.getFitness().sameOf(nextGenFitness)){
		    	env.totIdenticalFitnesses++;
		        if(stopVerifier.isStable(env.totIdenticalFitnesses)){
		        	fireStableSolutionEvent();
		        	endConditionFound = true;
		        }
		    }
		    else {
		    	env.totIdenticalFitnesses = 0; // reset if doesn't match
		    }
		}
		
		// Check goal reached
		if(!endConditionFound && env.currentGen.bestMatch.getFitness().fit()){
		    fireGoalReachedEvent();
		    env.targetReached = true;
		    endConditionFound = true;
		}
		
		// Check time stop
		if(!endConditionFound && !stopVerifier.onTime(env.currentGenNumber, EnvUtil.getLifeTimeInMillis(env))){
			fireHistoryEndedEvent();
		    endConditionFound = true;
		}
		return endConditionFound;
	}
	

	
	
	/**
	 * ============================================================
	 * 		OPERATORS
	 * ============================================================
	 */

	private Population selection(int generationSize) {
		Population nextGeneration = context.selector.select(env.currentGen);
		fireNewGenerationEvent();
		if(generationSize!=nextGeneration.solutions.size()){
			String msg = String.format("Selected generation size (%d) differs from last (%d)", nextGeneration.solutions.size(), generationSize);
			logger.severe(msg);
			throw new IllegalStateException(msg);
		}
		return nextGeneration;
	}

	private List<Solution> recombination(Solution[] parents) {
		List<Solution> sons;
		boolean crossover = RANDOMIZER.nextDouble() < context.parameters.recombinationPerc;
		if(crossover) {
		    sons = context.recombinator.recombine(Arrays.asList(parents));
		    fireCrossoverEvent(parents[0], parents[1], sons);
		}
		else {
			sons = Arrays.asList(parents[0].clone(), parents[1].clone());            
		}
		return sons;
	}

	private void mutation(List<Solution> sons) {
		boolean mute0 = RANDOMIZER.nextDouble() < context.parameters.mutationPerc;
		boolean mute1 = RANDOMIZER.nextDouble() < context.parameters.mutationPerc;
		if(mute0) { 
		    Solution old = sons.get(0);
		    Solution niu = old.clone();
		    context.mutator.mutate(niu);
		    sons.set(0, niu);
		    fireMutationEvent(old, niu);
		}
		if(mute1) { 
		    Solution old = sons.get(1);
		    Solution niu = old.clone();
		    context.mutator.mutate(niu);
		    sons.set(1, niu);
		    fireMutationEvent(old, niu);
		}
	}
	
	
	
	
	/**
	 * ============================================================
	 * 		EVENTS
	 * ============================================================
	 */

    private void fireNewGenerationEvent(){
        observer.newGenerationEvent(env.currentGenNumber+1, env.currentGen);
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
    
    
}
