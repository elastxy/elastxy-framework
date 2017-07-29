package it.red.algen.engine;

import java.util.Calendar;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.Env;
import it.red.algen.domain.Fitness;
import it.red.algen.domain.Population;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;

public class Evolver {
	
    public AlgorithmContext context;

    // WORKING DATA
    private Env env;

    // LISTENER
    private EnvObserver listener;
    
    
    
    public Evolver(AlgorithmContext context, Env env){
    	this.context = context;
    	this.env = env;
    }
    
    
    public void subscribe(EnvObserver l){
        listener = l;
        if(env.currentGen!=null) env.currentGen.subscribe(l);
    }




    /** Avvia la vita del sistema.
     */
    public void evolve(){
        // Azzera il tempo
        env.startTime = Calendar.getInstance().getTimeInMillis();
        
        // Testa la popolazione iniziale
        env.currentGen.testFitness(env.target);
//        _generationsHistory.add(_currentGen);
        fireNewGenerationEvent();
        
        boolean endConditionFound = false;
        
        // Finch� si trova la soluzione o il numero max 
        // di iterazioni � raggiunto, o il tempo di vita del sistema non termina, prosegue
        while(!env.currentGen.isGoalReached() && context.onTime(env.currentGenNumber, getLifeTimeInMillis())) {
        	
        	// Save last gen
        	Population lastGen = env.currentGen;
        	
        	// Create new gen
        	env.currentGen = env.currentGen.nextGen();
            
            // Test fitness of population
            Fitness currentGenFitness = env.currentGen.testFitness(env.target);
            Fitness bestMatchFitness = lastGen.getBestMatch().getFitness();
            
            // Check stability of the fitness value
            if(context.parameters._elitarism){
	            if(bestMatchFitness.sameOf(currentGenFitness)){
	            	env.totIdenticalFitnesses++;
	                if(context.isStable(env.totIdenticalFitnesses)){
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
            if(env.currentGen.isGoalReached()){
                fireGoalReachedEvent();
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
        stats._generationHistory = env.generationsHistory;
        return stats;
    }


    private void fireNewGenerationEvent(){
        listener.newGenerationEvent(env.currentGenNumber+1, env.currentGen);
    }
    
    private void fireGoalReachedEvent(){
        listener.goalReachedEvent(this);
    }

    private void fireStableSolutionEvent(){
        listener.stableSolutionEvent(this);
    }
    
    private void fireHistoryEndedEvent(){
        listener.historyEndedEvent(this);
    }
    
    
}
