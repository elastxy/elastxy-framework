/*
 * Popolazione.java
 *
 * Created on 4 agosto 2007, 13.47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import it.red.algen.tracking.EnvObserver;

/** Contiene la popolazione di soluzioni
 *
 * @author grossi
 */
public class Population {
    private static Random RANDOMIZER = new Random();

    private AlgParameters _algParameters;
    
    private List<Solution> _solutions;
    private Solution _bestMatch;
    
    private EnvObserver _listener;
    
    public Population(AlgParameters algParameters) {
        _solutions = new ArrayList<Solution>();
        _algParameters = algParameters;
    }
    
    public void subscribe(EnvObserver l){
        _listener = l;
    }
    
    public int size(){
    	return _solutions.size();
    }
    
    public void add(Solution solution){
        _solutions.add(solution);
    }
    
    public boolean isGoalReached(){
        return _bestMatch.getFitness().fit();
    }
    
    public Solution getBestMatch(){
        return _bestMatch;
    }
    
    /** Per ogni soluzione, calcola il fitness e tiene memorizzata la migliore.
     */
    public Fitness testFitness(Target target){
        _bestMatch = null;
        Iterator<Solution> it = _solutions.iterator();
        while(it.hasNext()){
            Solution solution = it.next();
            solution.calcFitness(target);
            if(solution.legalCheck()!=null) {
                fireIllegalSolutionEvent(solution);
            }
            else {
                fireFitnessCalculatedEvent(solution);
            }
            if(_bestMatch==null || (_bestMatch!=null && solution.getFitness().greaterThan(_bestMatch.getFitness()))){
                _bestMatch = solution;
            }
        }
        return _bestMatch.getFitness();
    }
    
    
    /** SELEZIONE 
     * Crea la successiva popolazione:
     * 	- parte da una lista della popolazione attuale mischiata
     * 
     *  - inserisce il clone del bestMatch, se presente, nella generazione successiva e lo elimina dalla lista 
     *  - estrare due individui per volta dalla popolazione corrente
     *  - effettua, con le probabilit� note, una ricombinazione e/o una mutazione
     *  - inserisce i due figli nella prossima generazione e li elimina dalla lista
     *  
     *  ..finche' la lista e' vuota
     */
    public Population nextGen(){
    	
    	// Creazione lista
    	List<Solution> actualPopulation = new ArrayList<Solution>(this._solutions);
    	Collections.shuffle(actualPopulation);
    	
        // Inserimento best match e rimozione fra quelli da valutare
        Population nextGen = new Population(_algParameters);
        nextGen.subscribe(_listener);
        if(_algParameters.getElitarism() && _bestMatch!=null){
            nextGen.add((Solution)_bestMatch.clone());
            actualPopulation.remove(_bestMatch);
        }
        
        // Finch� non si riempie la popolazione
        while(actualPopulation.size() > 0){

            // Caso di popolazione iniziale pari a 2 con best match individuato
            if(actualPopulation.size()==1){
            	nextGen.add((Solution)actualPopulation.remove(0).clone());
            	break;
            }
            
            // Estrazione due individui e generazione dei figli
            Solution father = actualPopulation.remove(0);
            Solution mother = actualPopulation.remove(0);
            Solution[] sons = null;
            
            // Crossover
            boolean crossover = RANDOMIZER.nextDouble() < _algParameters.getRecombinationPerc();
            if(crossover) {
                sons = father.crossoverWith(mother);
                fireCrossoverEvent(father, mother, sons);
            }
            else {
            	sons = new Solution[] {
                		(Solution)father.clone(), 
                		(Solution)mother.clone()
                };            
            }
            
            // Mutazione
            boolean mute0 = RANDOMIZER.nextDouble() < _algParameters.getMutationPerc();
            boolean mute1 = RANDOMIZER.nextDouble() < _algParameters.getMutationPerc();
            if(mute0) { 
                Solution old = sons[0];
                sons[0] = (Solution)sons[0].clone();
                sons[0].mute();
                fireMutationEvent(old, sons[0]);
            }
            if(mute1) { 
                Solution old = sons[1];
                sons[1] = (Solution)sons[1].clone();
                sons[1].mute(); 
                fireMutationEvent(old, sons[1]);
            }
            
            // Aggiungo i due individui alla nuova popolazione
            nextGen.add(sons[0]);
            nextGen.add(sons[1]);
            
            // Caso di popolazione pari e elitarismo o popolazione dispari:
            // l'ultimo non verra' valutato, ma inserito d'ufficio nella successiva per mantenere il numero
            if(actualPopulation.size()==1){
            	nextGen.add((Solution)actualPopulation.remove(0).clone());
            }
        }
        return nextGen;
    }
    
    
    private void fireFitnessCalculatedEvent(Solution s){
        _listener.fitnessCalculatedEvent(s);
    }
    
    private void fireIllegalSolutionEvent(Solution s){
        _listener.illegalSolutionEvent(s);
    }
    
    private void fireCrossoverEvent(Solution father, Solution mother, Solution[] sons){
        _listener.crossoverEvent(father, mother, sons);
    }
    
    private void fireMutationEvent(Solution orig, Solution mutated){
        _listener.mutationEvent(orig, mutated);
    }
    
    
    public String toString(){
    	StringBuffer result = new StringBuffer();
    	for(Solution s : _solutions){
    		result.append(s).append("\n");
    	}
    	return result.toString();
    }
}
