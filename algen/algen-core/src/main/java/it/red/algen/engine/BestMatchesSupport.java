package it.red.algen.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;

public class BestMatchesSupport {

	  /**
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
		bestMatches.add(nextGeneration.solutions.remove(0));
		
	    // Caso di elitarismo e popolazione pari: anche il successivo deve essere inserito
	    // per mantenere il numero delle coppie
	    if(generationSize > 1 && generationSize % 2 == 0){
	    	bestMatches.add(nextGeneration.solutions.remove(0));;
	    }
		return bestMatches;
	}
	
	
}
