/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
		
		int tot = lastBestMatches.size();
		for(int s=tot-1; s >= 0; s--){
			newGeneration.solutions.add(0, lastBestMatches.get(s));
		}
	}
	
	
}
