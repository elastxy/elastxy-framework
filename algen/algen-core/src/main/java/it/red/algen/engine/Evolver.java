package it.red.algen.engine;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.Env;
import it.red.algen.domain.Fitness;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObservable;
import it.red.algen.tracking.EnvObserver;

public class Evolver implements EnvObservable {

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




    /** Avvia la vita del sistema.
     */
    public void evolve(){
    	
        // Azzera il tempo
        env.startTime = Calendar.getInstance().getTimeInMillis();
        
        // Testa la popolazione iniziale
        fitnessTester.test(env.target, env.currentGen);
        fireNewGenerationEvent();
        
        boolean endConditionFound = false;
        
        // Finch� si trova la soluzione o il numero max 
        // di iterazioni � raggiunto, o il tempo di vita del sistema non termina, prosegue
        while(!isGoalReached(env.currentGen) && stopVerifier.onTime(env.currentGenNumber, getLifeTimeInMillis())) {
        	
        	// Save last gen
        	// -----------------------------------------------
        	Population lastGen = env.currentGen;
        	
        	
        	// SELECTION
        	// -----------------------------------------------
        	env.currentGen = context.selector.select(env.currentGen);
        	int currentGenSize = env.currentGen.solutions.size();
        	int startSolution = 0;
        	
        	// Best match insertion
            // TODOM: best matches a certain percentage, not only one
        	// TODOA: valutare eliminazione variabile best match
            if(context.parameters._elitarism){
            	startSolution++;
            	
                // Caso di elitarismo e popolazione pari: anche il successivo deve essere inserito
                // per mantenere il numero delle coppie
                if(currentGenSize > 1 && currentGenSize % 2 == 0){
                	startSolution++;
                }            
            }

            
        	// Per ogni coppia effettua ricombinazione e mutazione
        	for(int s=startSolution; s < currentGenSize; s=s+2){
                
        		// Estrazione due individui
                Solution father = env.currentGen.solutions.get(s);
                Solution mother = env.currentGen.solutions.get(s+1);
                List<Solution> sons = null;

                
                // RECOMBINATION
            	// -----------------------------------------------
                boolean crossover = RANDOMIZER.nextDouble() < context.parameters._recombinationPerc;
                if(crossover) {
                    sons = context.recombinator.crossoverWith(Arrays.asList(father, mother));
                    fireCrossoverEvent(father, mother, sons);
                }
                else {
                	sons = Arrays.asList(father.clone(),mother.clone());            
                }
                
                
                // MUTATION
            	// -----------------------------------------------
                boolean mute0 = RANDOMIZER.nextDouble() < context.parameters._mutationPerc;
                boolean mute1 = RANDOMIZER.nextDouble() < context.parameters._mutationPerc;
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
        	
                // Aggiungo i due individui alla nuova popolazione
                env.currentGen.solutions.set(s, sons.get(0));
                env.currentGen.solutions.set(s, sons.get(1));

        	}
        	
        	
            // Test fitness of population
        	// -----------------------------------------------
            Fitness currentGenFitness = fitnessTester.test(env.target, env.currentGen);
            Fitness bestMatchFitness = lastGen.bestMatch.getFitness();
            
            // Check stability of the fitness value
            if(context.parameters._elitarism){
	            if(bestMatchFitness.sameOf(currentGenFitness)){
	            	env.totIdenticalFitnesses++;
	                if(stopVerifier.isStable(env.totIdenticalFitnesses)){
	                	fireStableSolutionEvent();
	                	endConditionFound = true;
	                	break;
	                }
	            }
	            else {
	            	env.totIdenticalFitnesses = 0; // reset if doesn't match
	            }
            }
            
            // Determine end condition
            if(isGoalReached(env.currentGen)){
                fireGoalReachedEvent();
                env.targetReached = true;
                endConditionFound = true;
                break;
            }
            
            // Start new generation
//            _generationsHistory.add(_currentGen);
            env.currentGenNumber++;
            fireNewGenerationEvent();
        }
        
        // Naturally end history for this environment
        if(!endConditionFound) {
        	fireHistoryEndedEvent();
        }
        
        
        // END OF EXPERIMENT
        env.endTime = getLifeTimeInMillis();
    }
    

    /** Ritorna il tempo totale di vita del sistema in secondi.
     */
    public long getLifeTimeInMillis(){
        long now = Calendar.getInstance().getTimeInMillis();
        return now - env.startTime;
    }
    
    public ExperimentStats getStats(){
        ExperimentStats stats = new ExperimentStats();
        stats._target = env.target;
        stats._lastGeneration = env.currentGen;
        stats._generations = env.currentGenNumber+1;
        stats._time = env.endTime;
        stats._totIdenticalFitnesses = env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
        stats._generationHistory = env.generationsHistory;
        return stats;
    }


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
    

    private boolean isGoalReached(Population generation){
        return generation.bestMatch.getFitness().fit();
    }
    
    
}
