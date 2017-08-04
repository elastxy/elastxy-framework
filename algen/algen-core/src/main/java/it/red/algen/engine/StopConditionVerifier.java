package it.red.algen.engine;

import it.red.algen.conf.StopConditions;

public class StopConditionVerifier {
	
	private StopConditions stopConditions;

	
	public StopConditionVerifier(StopConditions stopConditions){
		this.stopConditions = stopConditions;
	}
	
	
	public boolean onTime(int currentGenNumber, long currentLifeTime){
		
		// Time
		boolean result = stopConditions.maxLifetimeMs==-1 || currentLifeTime <= stopConditions.maxLifetimeMs;
		
		// Iterations
		result &= (stopConditions.maxIterations==-1 || currentGenNumber < stopConditions.maxIterations-1);
		
		return result;
	}
	
	public boolean isStable(int totIdenticalFitnesses){
		return stopConditions.maxIdenticalFitnesses!=-1 && totIdenticalFitnesses>=stopConditions.maxIdenticalFitnesses;
	}
	
}
