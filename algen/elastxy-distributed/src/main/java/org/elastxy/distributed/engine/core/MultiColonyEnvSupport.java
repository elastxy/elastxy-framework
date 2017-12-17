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
    
    // TODOM-2: Stats collection to be completed
    public static MultiColonyExperimentStats getStats(MultiColonyEnv env){
        MultiColonyExperimentStats stats = new MultiColonyExperimentStats();
        stats.target = env.target;
        stats.bestMatch = env.eraBestMatches.isEmpty() ? null : env.eraBestMatches.get(0);
        stats.goalAccumulator = env.goalAccumulator.isPresent() ? env.goalAccumulator.get().value() : 0;
        stats.eras = (int)env.currentEraNumber; // TODOA-2: era number is +1??
        stats.executionTimeMs = env.endTime;
        stats.totErasIdenticalFitnesses= env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
//        stats.generationHistory = env.generationsHistory;
        return stats;
    }
}
