package it.red.algen.engine.standard;

import java.util.ArrayList;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.Population;
import it.red.algen.domain.interfaces.Solution;
import it.red.algen.engine.Selector;
import it.red.algen.engine.interfaces.PopulationFactory;

public class StandardSelector implements Selector {

    public void setup(OperatorsParameters algParameters) {
    }

	public void setup(OperatorsParameters algParameters, PopulationFactory populationFactory){
		
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
    public Population select(Population actualGeneration){
    	
    	// Creation of new population, initially identical to actual
    	Population nextGen = new Population();
    	nextGen.solutions = new ArrayList<Solution>(actualGeneration.solutions);
    	
        return nextGen;
    }
    
    
}
