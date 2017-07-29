package it.red.algen.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;
import it.red.algen.tracking.EnvObserver;

public class StandardSelector implements Selector {
    private static Random RANDOMIZER = new Random();

    private OperatorsParameters algParameters;


    // LISTENER
    private EnvObserver observer;

    
    public void setup(OperatorsParameters algParameters) {
        this.algParameters = algParameters;
    }

    
    public void subscribe(EnvObserver l){
    	observer = l;
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
    public Population select(Population actualGeneration){
    	
    	// Creazione lista random
    	List<Solution> actualPopulation = new ArrayList<Solution>(actualGeneration._solutions);
    	Collections.shuffle(actualPopulation);
    	
        // Inserimento best match e rimozione fra quelli da valutare
        Population nextGen = new Population();
        if(algParameters._elitarism && actualGeneration.bestMatch!=null){
            nextGen.add((Solution)actualGeneration.bestMatch.clone());
            actualPopulation.remove(actualGeneration.bestMatch);
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
            boolean crossover = RANDOMIZER.nextDouble() < algParameters._recombinationPerc;
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
            boolean mute0 = RANDOMIZER.nextDouble() < algParameters._mutationPerc;
            boolean mute1 = RANDOMIZER.nextDouble() < algParameters._mutationPerc;
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
    
    
    
    private void fireCrossoverEvent(Solution father, Solution mother, Solution[] sons){
    	observer.crossoverEvent(father, mother, sons);
    }
    
    private void fireMutationEvent(Solution orig, Solution mutated){
    	observer.mutationEvent(orig, mutated);
    }
    
    
}
