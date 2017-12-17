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
        stats.lastGeneration = env.currentGen;
        stats.generations = env.currentGenNumber+1;
        stats.executionTimeMs = env.totalLifeTime;
        stats.totIdenticalFitnesses = env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
        stats.generationHistory = env.generationsHistory;
        return stats;
    }
}
