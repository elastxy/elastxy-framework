package it.red.algen.engine.operators;

import java.util.ArrayList;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genoma;

public class StandardSelector implements Selector<Genoma> {

	public void setup(AlgorithmContext context){
		// no parameters needed
	}

    /** SELEZIONE 
     * 
     * Select next generation:
     * - starts from the actual population, with fitness already calculated for every individual
     * - and ordered by fitness descending
     * - if elitism is enabled, selects the best match and puts it into the next population
     * - TODOM: inserts a certain percentage of the best individuals, not only one
	 * - TODOM: maintain 100% fit chromosomes from current population to the next
     * 
     *  ..until list is empty
     */
    public Population select(Population actualGeneration, Genoma genoma){
    	
    	// Creation of new population, initially identical to actual
    	Population nextGen = new Population();
    	nextGen.solutions = new ArrayList<Solution<?,?>>(actualGeneration.solutions);
    	
        return nextGen;
    }
    
    
}
