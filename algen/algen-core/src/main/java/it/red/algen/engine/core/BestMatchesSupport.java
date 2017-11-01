package it.red.algen.engine.core;

import java.util.ArrayList;
import java.util.List;

import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;

public class BestMatchesSupport {

	  /**
     * Extract N best matches from a population, leaving a copy of everyone
     * in the residual solutions for mixing their genetic material,
     * and removes the N worst.
     * 
     * The copy will also maintain the original phenotype, in case the solution
     * will be selected furthermore.
     * 
     * // TODOM: best matches a certain percentage, not only one
	 * 
     * @param generationSize
     * @param nextGeneration
     */
	public static List<Solution> extractBestMatches(Population nextGeneration, boolean elitism) {
		List<Solution> bestMatches = new ArrayList<Solution>();
		int generationSize = nextGeneration.size();
		if(!elitism){
			return bestMatches;
		}
		Solution bestMatch = nextGeneration.solutions.remove(0);
		bestMatches.add(bestMatch);
		
		// Removes worst solution and leaves a copy of the best match inside to preserve good genetic material
		nextGeneration.solutions.remove(nextGeneration.solutions.size()-1);
		Solution bestBrother = bestMatch.copy();
		bestBrother.setPhenotype(bestMatch.getPhenotype());
		nextGeneration.solutions.add(bestBrother);
		
	    // Caso di elitarismo e popolazione pari: anche il successivo deve essere inserito
	    // per mantenere il numero delle coppie
	    if(generationSize > 1 && generationSize % 2 == 0){
	    	bestMatches.add(nextGeneration.solutions.remove(0));
	    }
		return bestMatches;
	}
	
	
}
