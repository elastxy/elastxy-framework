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

import java.util.Calendar;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.stats.ExperimentStats;

public class EnvSupport {


	public static Env startTime(Env environment) {
		environment.startTime = Calendar.getInstance().getTimeInMillis();
		environment.endTime = -1;
		environment.totalLifeTime = -1;
		return environment;
	}
    
	public static Env stopTime(Env env) {
		env.totalLifeTime = getLifeTimeInMillis(env);
		return env;
	}
	

    /** Ritorna il tempo totale di vita del sistema in secondi.
     */
    public static long getLifeTimeInMillis(Env env){
        long now = Calendar.getInstance().getTimeInMillis();
        env.endTime = now;
        return env.endTime - env.startTime;
    }
    

    public static ExperimentStats getStats(Env env){
        ExperimentStats stats = new ExperimentStats();
        stats.target = env.target;
        stats.bestMatch = env.lastGen.bestMatch;
        stats.lastGeneration = env.lastGen;
        stats.generations = env.lastGenNumber+1;
        stats.executionTimeMs = env.totalLifeTime;
        stats.totIdenticalFitnesses = env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
        stats.generationHistory = env.generationsHistory;
        return stats;
    }
}
