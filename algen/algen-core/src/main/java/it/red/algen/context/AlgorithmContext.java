package it.red.algen.context;

import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;
import it.red.algen.conf.OperatorsParameters;
import it.red.algen.conf.StopConditions;

public class AlgorithmContext {
	public OperatorsParameters parameters;
	public StopConditions stopConditions;
	public MonitoringConfiguration monitoringConfiguration;
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
	
	
	// TODOA: move into engine
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
