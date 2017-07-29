package it.red.algen.engine;

import java.util.Calendar;
import java.util.Iterator;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.Env;
import it.red.algen.domain.Fitness;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;
import it.red.algen.domain.Target;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObservable;
import it.red.algen.tracking.EnvObserver;

public class Evolver implements EnvObservable {
	
	// ALGORITHM PARAMETERS
    public AlgorithmContext context;
    private Selector selector;
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
    public Evolver(AlgorithmContext context, Env env, Selector selector){
    	this.context = context;
    	this.env = env;
    	this.selector = selector;
    	this.stopVerifier = new StopConditionVerifier(context.stopConditions);
    }
    
    
    public void subscribe(EnvObserver l){
        observer = l;
        selector.subscribe(l);
    }




    /** Avvia la vita del sistema.
     */
    public void evolve(){
    	
        // Azzera il tempo
        env.startTime = Calendar.getInstance().getTimeInMillis();
        
        // Testa la popolazione iniziale
        testFitness(env.target, env.currentGen);
        fireNewGenerationEvent();
        
        boolean endConditionFound = false;
        
        // Finch� si trova la soluzione o il numero max 
        // di iterazioni � raggiunto, o il tempo di vita del sistema non termina, prosegue
        while(!isGoalReached(env.currentGen) && stopVerifier.onTime(env.currentGenNumber, getLifeTimeInMillis())) {
        	
        	// Save last gen
        	Population lastGen = env.currentGen;
        	
        	// Create new gen
        	env.currentGen = selector.select(env.currentGen);
            
            // Test fitness of population
            Fitness currentGenFitness = testFitness(env.target, env.currentGen);
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
    
    private void fireGoalReachedEvent(){
        observer.goalReachedEvent(this);
    }

    private void fireStableSolutionEvent(){
        observer.stableSolutionEvent(this);
    }
    
    private void fireHistoryEndedEvent(){
        observer.historyEndedEvent(this);
    }
    
    

    private void fireFitnessCalculatedEvent(Solution s){
        observer.fitnessCalculatedEvent(s);
    }
    
    private void fireIllegalSolutionEvent(Solution s){
    	observer.illegalSolutionEvent(s);
    }
    


    private boolean isGoalReached(Population generation){
        return generation.bestMatch.getFitness().fit();
    }
    
    
    /** Per ogni soluzione, calcola il fitness e tiene memorizzata la migliore.
     * 
     * TODOM: create a FitnessTester interface
     */
    public Fitness testFitness(Target target, Population population){
    	population.bestMatch = null;
        Iterator<Solution> it = population._solutions.iterator();
        while(it.hasNext()){ // TODOA: MapReduce!
            Solution solution = it.next();
            solution.calcFitness(target);
            if(solution.legalCheck()!=null) {
                fireIllegalSolutionEvent(solution);
            }
            else {
                fireFitnessCalculatedEvent(solution);
            }
            if(population.bestMatch==null || 
            		(population.bestMatch!=null && solution.getFitness().greaterThan(population.bestMatch.getFitness()))){
            	population.bestMatch = solution;
            }
        }
        return population.bestMatch.getFitness();
    }
    
}
