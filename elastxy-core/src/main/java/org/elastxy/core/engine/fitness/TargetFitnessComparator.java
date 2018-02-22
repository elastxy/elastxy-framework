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
package org.elastxy.core.engine.fitness;

import java.math.BigDecimal;
import java.util.Comparator;

import org.elastxy.core.domain.experiment.Solution;

/**
 * Compares two solutions fitness by absolute proximity to the given fitness target, ordering in ASC order
 * 
 * If one is null, puts it at the end.
 * If both are null, the first is chosen
 * 
 * @author red
 */

@SuppressWarnings("rawtypes")
public class TargetFitnessComparator implements Comparator<Solution> {
	private BigDecimal targetFitness;
	
	public TargetFitnessComparator(BigDecimal targetFitness){
		this.targetFitness = targetFitness;
	}
	
	
	@Override
	public int compare(Solution arg1, Solution arg2) {
		BigDecimal fitness1 = arg1.getFitness()==null ? null : arg1.getFitness().getValue();
		BigDecimal fitness2 = arg2.getFitness()==null ? null : arg2.getFitness().getValue();
		int result = 0;
		if(fitness1==null && fitness2!=null){
			result = 1;
		}
		else if(fitness1!=null && fitness2==null){
			result = -1;
		}
		else if(fitness1==null && fitness2==null){
			result = 0;
		}
		else if(fitness1.compareTo(fitness2)==0){
			result = 0;
		}
		else {
			result = arg1.getFitness().nearestThan(arg2.getFitness(), targetFitness) ? 2 : -2; 
		}
		
		// MINUS SIGN => reverse order, from higher to lower
		return -result;
	}

}
