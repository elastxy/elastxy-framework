package org.elastxy.core.engine.operators;

import java.util.ArrayList;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;

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
     * - TODOA-4: inserts a certain percentage of the best individuals, not only one
	 * - TODOM-8: multiple chromosomes: maintain 100% fit chromosomes from current population to the next
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
