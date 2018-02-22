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
package org.elastxy.core.tracking;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.elastxy.core.domain.experiment.Population;

public class ASCIIGraphRenderer {
	
	/**
	 * Step adapts to generations speed: first order of size fitting max 250 ms
	 *
	 * @param number
	 * @param executionTime
	 * @return
	 */
	public static int adaptStepToSpeed(int number, long executionTime) {
		int step = 1;
		if(number==0){ // first gen: canno check speed if number is 0
			number += 1;
		}
		long timePerGen = Math.max(1, executionTime / number);
		long timePerStep = timePerGen * step;
		while(timePerStep < 250){
			step *= 10;
			timePerStep = timePerGen * step;
		}
		return step;
	}
	

	public static String displayGraph(int number, Population lastGen, int step, boolean showBestMatch) {
		StringBuffer result = new StringBuffer();
		
		// Header
		if(number == 0){
			String line = String.format("        |0---10---20---30---40---50---60---70---80---90---100");
			result = result.append(line);
		}
		
		// Rows
		else if((number > 0 && number < 10) || // first 10 unit
				((number > 0 && number < 1000) && number % 100 == 0) // first 1000 every 100
				|| number % step == 0){ // every step
				double lastFitness = lastGen.bestMatch.getFitness().getValue().doubleValue();
				int points = (int)Math.ceil(lastFitness*100.0/2.0);
				String pointsChars = IntStream.rangeClosed(0, points).mapToObj(i -> "-").collect(Collectors.joining());
				String emptyChars =  IntStream.rangeClosed(0, 50-points).mapToObj(i -> " ").collect(Collectors.joining());
				StringBuffer line = new StringBuffer();
				line = line.append(String.format("%8d|", number));
				line.append(pointsChars);
				line.append(emptyChars);
				line.append(String.format("| %.10f |",lastFitness));
				if(showBestMatch) line.append(String.format("%30.200s", lastGen.bestMatch));
				result = result.append(line.toString());
		}
		
		return result.toString();
	}
    
}
