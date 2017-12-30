package org.elastxy.core.engine.core;

import java.util.List;
import java.util.stream.Collectors;

import org.elastxy.core.conf.ElitismParameters;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;

public class BestMatchesSupport {
	

	/**
	 * Calculates the number of best matches to maintain.
	 * 
	 * @param eliteNumber
	 * @param elitePerc
	 * @param generationSize
	 * @return
	 */
	public static Long calculateBestMatchesNumber(Long eliteNumber, Double elitePerc, long generationSize) {
		Long bestMatchesNumber = eliteNumber;
		if(bestMatchesNumber==null || eliteNumber < 0){
			bestMatchesNumber = (long)Math.floor(generationSize * elitePerc);
		}

		// if the number of best is greater than half, internal error: 
		// the total number of best can't be greater than worst!
		if(bestMatchesNumber > generationSize / 2){
			throw new AlgorithmException(String.format("Number of better solution (%d) cannot be greater than half population ()! Please reduce best matches number.", bestMatchesNumber, (generationSize / 2)));
		}
		return bestMatchesNumber;
	}
	
	
	
	/**
     * Given a population of solutions ordered by fitness, extract N best matches, and put them into a bestMatches list.
     */
	public static void calculateBestMatches(Population population, ElitismParameters elitism) {
		
		// no elitism => does nothing
		if(!elitism.singleColonyElitism){
			return;
		}
		
		// elitism => check the number of best matches to retain
		int generationSize = population.size();
		Long bestMatchesNumber = calculateBestMatchesNumber(
				elitism.singleColonyElitismNumber, 
				elitism.singleColonyElitismPerc,
				generationSize);
		
		// limit best matches to given number
		population.bestMatches = population.solutions.stream().
         		limit(bestMatchesNumber).
         		collect(Collectors.toList());
	}
	
	
	
	/**
	 * Reinsert on a population all previously selected best matches,
	 * to preserve the total solutions number and best quality individuals.
	 * 
	 * @param lastBestMatches
	 * @param newGeneration
	 */
	public static void reinsertBestMatches(List<Solution<?,?>> lastBestMatches, Population newGeneration){
		if(lastBestMatches==null){
			return; // no best matches to reintroduce
		}
		
		for(int s=lastBestMatches.size()-1; s >= 0; s--){
			newGeneration.solutions.add(0, lastBestMatches.get(s));
		}
	}
	
	
}
