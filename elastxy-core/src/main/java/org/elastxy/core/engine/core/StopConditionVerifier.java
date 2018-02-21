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
