package it.red.algen.engine;

import java.util.ArrayList;
import java.util.Collections;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;

public class StandardSelector implements Selector {

	// TODOA: mettere anche in mutator e recombinator
    public void setup(OperatorsParameters algParameters) {
    }


    /** SELEZIONE 
     * 
     * Select next generation:
     * - starts from the actual population, with fitness already calculated for every individual
     * - and ordered by fitness descending
     * - if elitism is enabled, selects the best match and puts it into the next population
     * - TODOM: inserts a certain percentage of the best individuals
     * 
     *  ..finche' la lista e' vuota
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Population select(Population actualGeneration){
    	
    	// Creation of new population, initially identical to actual
    	Population nextGen = new Population();
    	nextGen.solutions = new ArrayList<Solution>(actualGeneration.solutions);
    	
        return nextGen;
    }
    
    
}
