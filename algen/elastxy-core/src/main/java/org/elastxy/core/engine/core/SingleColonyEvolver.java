package org.elastxy.core.engine.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.engine.fitness.FitnessTester;
import org.elastxy.core.engine.fitness.StandardFitnessTester;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObserver;

public class SingleColonyEvolver implements Evolver {
	private static Logger logger = Logger.getLogger(SingleColonyEvolver.class.getName());

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
    public SingleColonyEvolver(AlgorithmContext context, Env env){
    	this.context = context;
    	this.env = env;
    	this.fitnessTester = new StandardFitnessTester(context.application.fitnessCalculator);
    	this.stopVerifier = new StopConditionVerifier(context.algorithmParameters.stopConditions);
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
    	if(context.monitoringConfiguration.traceHistory) env.generationsHistory.add(env.currentGen);
        
    	// TEST FITNESS - initial gen
    	// TODOA-4: selection-operators-fitness-check also for first generation, bypassing operators
        fitnessTester.test(env.currentGen, env);
        int generationSize = env.currentGen.solutions.size();
        Fitness bestFitness = env.currentGen.bestMatch.getFitness();
        boolean endConditionFound = checkEndCondition(null, bestFitness);
//        fireNewGenerationEvent(null, env.currentGen);
        
        
    	// Loops until end condition rises
        while(!endConditionFound) {
        	
        	// SELECTION
        	Population currentGeneration = selection(generationSize);
        	if(context.monitoringConfiguration.traceHistory) env.generationsHistory.add(currentGeneration);
        	
        	// For uniform distribution selector skip genetic operators
        	if(!context.algorithmParameters.randomEvolution) {
        		applyGeneticOperators(generationSize, currentGeneration);
        	}
        	
            // TEST FITNESS - next gen
            fitnessTester.test(currentGeneration, env);

            // Assign new generation
            bestFitness = currentGeneration.bestMatch.getFitness();
            Fitness lastFitness = env.currentGen.bestMatch.getFitness();

            env.currentGen = currentGeneration;
            env.currentGenNumber++;
            
            // CHECK END CONDITION
            endConditionFound = checkEndCondition(lastFitness, bestFitness);
        }
        
        // END OF EXPERIMENT
    }


	private void applyGeneticOperators(int generationSize, Population nextGeneration) {
		
		// BEST MATCHES - extract
		// TODOA-2: reuse some best matches for sharing their genetic material
		List<Solution> bestMatches = BestMatchesSupport.extractBestMatches(nextGeneration, context.algorithmParameters.elitarism);

		// LOOP OVER NON-BEST SHUFFLED
        Collections.shuffle(nextGeneration.solutions);
		for(int s=0; s < generationSize-bestMatches.size(); s=s+2){
		    
			// EXTRACT PARENTS
			Solution[] parents = {nextGeneration.solutions.get(s), nextGeneration.solutions.get(s+1)};

		    
		    // RECOMBINATION or PRESERVATION
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

	private boolean checkEndCondition(Fitness lastGenFitness, Fitness currentGenFitness) {
		boolean endConditionFound = false;
		
		// Check fitness
		if(!endConditionFound && env.currentGen.bestMatch.getFitness().fit(
				env.target.getTargetThreshold(), 
				env.target.getTargetFitness())) {
			endConditionFound = goalReached();
		}
		
		
		// Check stability of the fitness value
		if(!endConditionFound && context.algorithmParameters.elitarism){
		    if(lastGenFitness!=null && currentGenFitness.sameOf(lastGenFitness)){
		    	env.totIdenticalFitnesses++;
		        if(stopVerifier.isStable(env.totIdenticalFitnesses)){
		        	EnvSupport.stopTime(env);
				    env.targetReached = true;
		        	endConditionFound = true;
		        	fireStableSolutionEvent();
		        }
		    }
		    else {
		    	env.totIdenticalFitnesses = 0; // if doesn't match, reset and starts over
		    }
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
		Population currentGeneration = context.application.selector.select(env.currentGen, env.genoma);
		fireNewGenerationEvent(env.currentGen, currentGeneration);
		if(generationSize!=currentGeneration.solutions.size()){
			String msg = String.format("Selected generation size (%d) differs from last (%d)", currentGeneration.solutions.size(), generationSize);
			logger.severe(msg);
			throw new IllegalStateException(msg);
		}
		return currentGeneration;
	}

	
	private List<Solution> recombination(Solution[] parents) {
		List<Solution> sons;
		boolean crossover = Randomizer.nextDouble() < context.algorithmParameters.recombinationPerc;
		if(crossover) {
			// Phenotype and fitness are not propagated to offsprings because they will likely change after recombination
		    sons = context.application.recombinator.recombine(Arrays.asList(parents), env.genoma.getLimitedAllelesStrategy());
		    fireCrossoverEvent(parents[0], parents[1], sons);
		}
		else {
			sons = Arrays.asList(parents[0].copy(), parents[1].copy());            
		}
		return sons;
	}

	
	private void mutation(List<Solution> sons) {
		boolean mute0 = Randomizer.nextDouble() < context.algorithmParameters.mutationPerc;
		boolean mute1 = Randomizer.nextDouble() < context.algorithmParameters.mutationPerc;
		if(mute0) { 
		    Solution old = sons.get(0);
		    Solution niu = old.copyGenotype();
		    context.application.mutator.mutate(niu, env.genoma);
		    sons.set(0, niu);
		    fireMutationEvent(old, niu);
		}
		if(mute1) { 
		    Solution old = sons.get(1);
		    Solution niu = old.copyGenotype();
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
        observer.newGenerationEvent(env.currentGenNumber, EnvSupport.getLifeTimeInMillis(env), lastGen, newGen);
    }

    private void fireCrossoverEvent(Solution father, Solution mother, List<Solution> sons){
    	observer.crossoverEvent(father, mother, sons);
    }
    
    private void fireMutationEvent(Solution orig, Solution mutated){
    	observer.mutationEvent(orig, mutated);
    }
    
    private void fireGoalReachedEvent(){
        observer.goalReachedEvent(getStats());
    }

    private void fireStableSolutionEvent(){
        observer.stableSolutionEvent(getStats());
    }
    
    private void fireHistoryEndedEvent(){
        observer.historyEndedEvent(getStats());
    }
    
    
    public String toString(){
    	return String.format("Evolver: current Env %s", env);
    }
    
}
