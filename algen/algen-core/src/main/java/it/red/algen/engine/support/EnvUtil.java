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
        stats._target = env.target;
        stats._lastGeneration = env.currentGen;
        stats._generations = env.currentGenNumber+1;
        stats._time = env.endTime;
        stats._totIdenticalFitnesses = env.totIdenticalFitnesses;
        stats.targetReached = env.targetReached;
        stats._generationHistory = env.generationsHistory;
        return stats;
    }
}
