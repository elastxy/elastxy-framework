/*
 * Env.java
 *
 * Created on 4 agosto 2007, 14.04
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;

/** Ambiente in cui la popolazione evolve in base al target.
 *  E' qui che avviene la logica di evoluzione.
 *  
 *  TODOA: remove algorithm logics
 *
 * @author grossi
 */
public class Env {

	private AlgorithmContext _context;
	
    // DATI CORRENTI
    private Population _currentGen;
    private int _currentGenNumber;
    private int _totIdenticalFitnesses = 0; // total of subsequent best matches with same fitness value
    
    // PARAMETRI
    private Target _target;
            
    // STORICO
    private List<Population> _generationsHistory;
    private long _startTime;
    
    // LISTENER
    private EnvObserver _listener;
    
    public void init(AlgorithmContext context, Population startGen, Target target) {
    	_context = context;
    	_currentGen = startGen;
        _target = target;
        _generationsHistory = new ArrayList<Population>();
    }
    
    public void subscribe(EnvObserver l){
        _listener = l;
        if(_currentGen!=null) _currentGen.subscribe(l);
    }
    
    public ExperimentStats getStats(){
        ExperimentStats stats = new ExperimentStats();
        stats._target = _context.applicationSpecifics.target;
        stats._lastGeneration = _currentGen;
        stats._generations = _currentGenNumber+1;
        stats._time = getLifeTimeInMillis();
        stats._totIdenticalFitnesses = _totIdenticalFitnesses;
        stats._generationHistory = _generationsHistory;
        return stats;
    }
    
    /** Ritorna il tempo totale di vita del sistema in secondi.
     */
    public long getLifeTimeInMillis(){
        long now = Calendar.getInstance().getTimeInMillis();
        return now - _startTime;
    }
    
    /** Avvia la vita del sistema.
     */
    public void evolve(){
        // Azzera il tempo
        _startTime = Calendar.getInstance().getTimeInMillis();
        
        // Testa la popolazione iniziale
        _currentGen.testFitness(_target);
//        _generationsHistory.add(_currentGen);
        fireNewGenerationEvent();
        
        boolean endConditionFound = false;
        
        // Finch� si trova la soluzione o il numero max 
        // di iterazioni � raggiunto, o il tempo di vita del sistema non termina, prosegue
        while(!_currentGen.isGoalReached() && _context.onTime(_currentGenNumber, getLifeTimeInMillis())) {
        	
        	// Save last gen
        	Population lastGen = _currentGen;
        	
        	// Create new gen
            _currentGen = _currentGen.nextGen();
            
            // Test fitness of population
            Fitness currentGenFitness = _currentGen.testFitness(_target);
            Fitness bestMatchFitness = lastGen.getBestMatch().getFitness();
            
            // Check stability of the fitness value
            if(_context.parameters._elitarism){
	            if(bestMatchFitness.sameOf(currentGenFitness)){
	            	_totIdenticalFitnesses++;
	                if(_context.isStable(_totIdenticalFitnesses)){
	                	fireStableSolutionEvent();
	                	endConditionFound = true;
	                	break;
	                }
	            }
	            else {
	            	_totIdenticalFitnesses = 0; // reset if doesn't match
	            }
            }
            
            // Determine end condition
            if(_currentGen.isGoalReached()){
                fireGoalReachedEvent();
                endConditionFound = true;
                break;
            }
            
            // Start new generation
//            _generationsHistory.add(_currentGen);
            _currentGenNumber++;
            fireNewGenerationEvent();
        }
        
        // Naturally end history for this environment
        if(!endConditionFound) {
        	fireHistoryEndedEvent();
        }
    }
    
    private void fireNewGenerationEvent(){
        _listener.newGenerationEvent(_currentGenNumber+1, _currentGen);
    }
    
    private void fireGoalReachedEvent(){
        _listener.goalReachedEvent(this);
    }

    private void fireStableSolutionEvent(){
        _listener.stableSolutionEvent(this);
    }
    
    private void fireHistoryEndedEvent(){
        _listener.historyEndedEvent(this);
    }
    
    public List<Population> getGenerationsHistory(){
    	return _generationsHistory;
    }
}
