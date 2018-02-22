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

import org.elastxy.core.conf.StopConditions;


/**
 * Checks the stop condition given the algorithm parameters.
 * 
 * @author red
 *
 */
public class StopConditionVerifier {
	
	private StopConditions stopConditions;

	
	public StopConditionVerifier(StopConditions stopConditions){
		this.stopConditions = stopConditions;
	}
	
	
	public boolean onTime(int currentGenNumber, long currentLifeTime){
		
		// Time
		boolean result = stopConditions.maxLifetimeMs==-1 || currentLifeTime <= stopConditions.maxLifetimeMs;
		
		// Iterations
		result &= (stopConditions.maxGenerations==-1 || currentGenNumber < stopConditions.maxGenerations-1);
		
		return result;
	}
	
	public boolean isStable(int totIdenticalFitnesses){
		return stopConditions.maxIdenticalFitnesses!=-1 && totIdenticalFitnesses>=stopConditions.maxIdenticalFitnesses;
	}
	
}
