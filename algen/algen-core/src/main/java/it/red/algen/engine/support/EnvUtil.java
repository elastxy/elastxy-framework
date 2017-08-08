package it.red.algen.engine.support;

import java.util.Calendar;

import it.red.algen.domain.Env;
import it.red.algen.stats.ExperimentStats;

public class EnvUtil {


	public static Env startTime(Env environment) {
		environment.startTime = Calendar.getInstance().getTimeInMillis();
		return environment;
	}
    
	public static Env stopTime(Env env) {
		env.endTime = getLifeTimeInMillis(env);
		return env;
	}
	

    /** Ritorna il tempo totale di vita del sistema in secondi.
     */
    public static long getLifeTimeInMillis(Env env){
        long now = Calendar.getInstance().getTimeInMillis();
        return now - env.startTime;
    }
    

    public static ExperimentStats getStats(Env env){
        ExperimentStats stats = new ExperimentStats();
        stats.target = env.target;
        stats.lastGeneration = env.currentGen;
        stats.generations = env.currentGenNumber+1;
        stats.time = env.endTime;
        stats.totIdenticalFitnesses = env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
        stats.generationHistory = env.generationsHistory;
        return stats;
    }
}
