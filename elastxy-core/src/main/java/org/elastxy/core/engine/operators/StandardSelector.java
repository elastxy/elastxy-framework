package org.elastxy.core.engine.operators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.engine.core.BestMatchesSupport;

public class StandardSelector implements Selector<Genoma> {
	private AlgorithmContext context;
	
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	

    /** SELEZIONE 
     * 
     * Select next generation:
     * - starts from the actual population, with fitness and best matches already calculated 
     * 	 for every individual and solutions already ordered by fitness desc
     * - orders solutions by fitness descending
     * - if elitism is enabled:
     * 		. selects the best matches and puts a clone of them into the next population into bestMatches list
     * 		. discards worst matches
	 * - TODO1-8: Elitism: multiple chromosomes: maintain 100% fit chromosomes from current population to the next
     * 
     *  ..until list is empty
     */
    public Population select(Population actualGeneration, Genoma genoma){
    	Population newGeneration = new Population();
    	
//		actualGeneration.solutions = actualGeneration.solutions.stream().
//    			sorted(new FitnessComparator<Solution>()).
//         		collect(Collectors.toList());

    	// No elitism: creation of new population initially identical to previous
    	if(!context.algorithmParameters.elitism.singleColonyElitism){
    		newGeneration.solutions = new ArrayList<Solution<?,?>>(actualGeneration.solutions);
    	}
    	
    	// Elitism: cut worst solutions and preserve a number of good ones
    	else {
    		int generationSize = actualGeneration.solutions.size();
    		int bestMatchesNumber = actualGeneration.bestMatches == null ? 0 : actualGeneration.bestMatches.size();

    		newGeneration.solutions = new ArrayList<Solution<?,?>>();
    		
    		// Best fitness solutions are maintained
    		for(int s=0; s < bestMatchesNumber; s++){
    			Solution best = actualGeneration.bestMatches.get(s);
    			// TODO2-4: optimization: don't copy phenotype for solutions to be recombined/mutated
    			newGeneration.add(best.copy()); 
    		}
    		
    		// Middle quality solutions are maintained (implicitly worst are lost)
    		for(int s=bestMatchesNumber; s < generationSize-bestMatchesNumber; s++){
    			newGeneration.add(actualGeneration.solutions.get(s));
    		}
    		
    	}
    	
        return newGeneration;
    }
    
}
