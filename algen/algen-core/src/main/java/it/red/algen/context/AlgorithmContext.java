package it.red.algen.context;

import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;
import it.red.algen.conf.OperatorsParameters;
import it.red.algen.conf.StopConditions;
import it.red.algen.tracking.Reporter;

public class AlgorithmContext {
	public OperatorsParameters parameters;
	public StopConditions stopConditions;
	public MonitoringConfiguration monitoringConfiguration;
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
	
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
	
	
	
	public static AlgorithmContext build(
			long initialSelectionNumber,
			boolean initialSelectionRandom,
			double recombinationPerc,
			double mutationPerc,
			boolean elitarism,
			int maxIterations,
			int maxLifetimeMs,
			int maxIdenticalFitnesses,
			boolean verbose,
			Reporter reporter
			){
		
        OperatorsParameters algParameters = new OperatorsParameters();
        algParameters._recombinationPerc = recombinationPerc;
        algParameters._mutationPerc = mutationPerc;
        algParameters._elitarism = elitarism;
        algParameters._initialSelectionNumber = initialSelectionNumber;
        algParameters._initialSelectionRandom = initialSelectionRandom;
        
        StopConditions stopConditions = new StopConditions();
        stopConditions.maxIterations = maxIterations;
        stopConditions.maxLifetimeMs = maxLifetimeMs;
        stopConditions.maxIdenticalFitnesses = maxIdenticalFitnesses;
        
        MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
        monitoringConfiguration.verbose = verbose;
        monitoringConfiguration.reporter = reporter;
        
        AlgorithmContext context = new AlgorithmContext();
        context.parameters = algParameters;
        context.stopConditions = stopConditions;
        context.monitoringConfiguration = monitoringConfiguration;
        
        return context;
	}
}
