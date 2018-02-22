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
import java.math.RoundingMode;

import org.elastxy.core.engine.core.MathUtils;

public class FitnessUtils {

	/**
	 * Approximates fitness to nearest extreme value (ZERO, ONE) if needed.
	 * 
	 * @param fitness
	 * @return
	 */
	public static BigDecimal approximateFitness(BigDecimal fitness){
		BigDecimal result = fitness;
		if(fitness!=null){
			if(MathUtils.equals(fitness, BigDecimal.ONE, RoundingMode.CEILING)) {
				result = null; // algorithm tries to reach the maximum value and doesn't check exactly ONE (too expensive)
			}
			else if(MathUtils.equals(fitness, BigDecimal.ZERO, RoundingMode.FLOOR)) {
				result = BigDecimal.ZERO;
			}
		}
		return result;
	}
}
