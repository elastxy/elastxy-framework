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
package org.elastxy.distributed.engine.core;

import java.util.Calendar;

import org.elastxy.distributed.experiment.MultiColonyEnv;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

public class MultiColonyEnvSupport {


	public static MultiColonyEnv startTime(MultiColonyEnv environment) {
		environment.startTime = Calendar.getInstance().getTimeInMillis();
		return environment;
	}
    
	public static MultiColonyEnv stopTime(MultiColonyEnv env) {
		env.endTime = getLifeTimeInMillis(env);
		return env;
	}
	

    /** Ritorna il tempo totale di vita del sistema in secondi.
     */
    public static long getLifeTimeInMillis(MultiColonyEnv env){
        long now = Calendar.getInstance().getTimeInMillis();
        return now - env.startTime;
    }
    
    // FIXME: Stats collection to be completed
    public static MultiColonyExperimentStats getStats(MultiColonyEnv env){
        MultiColonyExperimentStats stats = new MultiColonyExperimentStats();
        stats.target = env.target;
        stats.bestMatch = env.allBestMatches.isEmpty() ? null : env.allBestMatches.get(0);
        stats.goalAccumulator = env.goalAccumulator.isPresent() ? env.goalAccumulator.get().value() : 0;
        stats.eras = (int)env.currentEraNumber;
        stats.eons = (int)env.currentEonNumber;
        stats.executionTimeMs = env.endTime;
        stats.totErasIdenticalFitnesses= env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
//        stats.generationHistory = env.generationsHistory;
        return stats;
    }
}
