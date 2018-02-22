/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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


/**
 * Evolve system life over generations in a single colony context.
 * 
 * @author red
 *
 */
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
    	this.fitnessTester = new StandardFitnessTester(context.application.fitnessCalculator, context.algorithmParameters.elitism);
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
    	
    	// START
    	EnvSupport.startTime(env);
    	
        // TEST FITNESS - initial gen
        fitnessTester.test(env.lastGen, env);
        long populationSize = env.lastGen.solutions.size();
        Fitness bestFitness = env.lastGen.bestMatch.getFitness();
        boolean endConditionFound = checkEndCondition(null, bestFitness);
        
        // FIXME: selection-operators-fitness-check also for first generation, bypassing operators
//    	boolean endConditionFound = false;
//    	long populationSize = context.algorithmParameters.initialSelectionNumber;
//    	if(context.monitoringConfiguration.traceHistory) env.generationsHistory.add(env.lastGen);
        
        
    	// Loops until end condition rises
        while(!endConditionFound) {
        	
        	// SELECTION
        	// Worst matches are excluded and a clone of best maintained
        	Population newGeneration = selection(env.lastGen, populationSize);
        	
        	// GENETIC OPERATORS
        	// Skip genetic operators in case of uniform distribution selector
        	if(!context.algorithmParameters.randomEvolution) {
        		applyGeneticOperators(newGeneration);
        	}
        	
            // TEST FITNESS
        	BestMatchesSupport.reinsertBestMatches(env.lastGen.bestMatches, newGeneration);
            fitnessTester.test(newGeneration, env);

            // Pass to new generation
            Fitness newBestFitness = newGeneration.bestMatch.getFitness();
            Fitness lastBestFitness = env.lastGenNumber > 0 ? env.lastGen.bestMatch.getFitness() : null;

            env.lastGen = newGeneration;
            env.lastGenNumber++;
            if(context.monitoringConfiguration.traceHistory) env.generationsHistory.add(newGeneration);
            
            // CHECK END CONDITION
            endConditionFound = checkEndCondition(lastBestFitness, newBestFitness);
        }
        
        // END OF EXPERIMENT
    }


	private void applyGeneticOperators(Population newGeneration) {
		
		// LOOP OVER NON-BEST SHUFFLED
        Collections.shuffle(newGeneration.solutions);
        int solutionsToOperate = newGeneration.solutions.size();
        if(solutionsToOperate % 2 != 0) solutionsToOperate--; // odd case: skip the last solution
		for(int s=0; s < solutionsToOperate; s=s+2){
		    
			// EXTRACT PARENTS
			Solution[] parents = {newGeneration.solutions.get(s), newGeneration.solutions.get(s+1)};

		    // RECOMBINATION
			List<Solution> sons = recombination(parents);
		    
		    // MUTATION
		    mutation(sons);
		
		    // REPLACE PARENTS WITH SONS
		    newGeneration.solutions.set(s, sons.get(0));
		    newGeneration.solutions.set(s+1, sons.get(1));
		}

	}


    

	

	/**
	 * ============================================================
	 * 		CHECK END CONDITION
	 * ============================================================
	 */

	private boolean checkEndCondition(Fitness lastGenFitness, Fitness currentGenFitness) {
		boolean endConditionFound = false;
		
		// Check fitness
		if(!endConditionFound && currentGenFitness.fit(
				env.target.getTargetThreshold(), 
				env.target.getTargetFitness())) {
			endConditionFound = goalReached();
		}
		
		
		// Check stability of the fitness value
		if(!endConditionFound && context.algorithmParameters.elitism.singleColonyElitism){
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
		if(!endConditionFound && !stopVerifier.onTime(env.lastGenNumber, EnvSupport.getLifeTimeInMillis(env))){
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

	private Population selection(Population lastGeneration, long generationSize) {
		Population newGeneration = context.application.selector.select(lastGeneration, env.genoma);
		fireNewGenerationEvent(env.lastGen, newGeneration);
		int bm = lastGeneration.bestMatches==null ? 0 : lastGeneration.bestMatches.size();
		if(generationSize!=newGeneration.solutions.size()+bm){
			String msg = String.format("Selected generation size (%d+%d) differs from last (%d)", 
					newGeneration.solutions.size(), 
					newGeneration.bestMatches.size(), 
					generationSize);
			logger.severe(msg);
			throw new AlgorithmException(msg);
		}
		return newGeneration;
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
        observer.newGenerationEvent(env.lastGenNumber, EnvSupport.getLifeTimeInMillis(env), lastGen, newGen);
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
