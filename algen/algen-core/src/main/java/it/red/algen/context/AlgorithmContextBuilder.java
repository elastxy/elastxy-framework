package it.red.algen.context;

import org.springframework.stereotype.Component;

import it.red.algen.conf.MonitoringConfiguration;
import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.conf.StopConditions;
import it.red.algen.tracking.Reporter;

@Component
public class AlgorithmContextBuilder {

	public AlgorithmContext build(
			long initialSelectionNumber,
			boolean initialSelectionRandom,
			double recombinationPerc,
			double mutationPerc,
			boolean elitarism,
			int maxIterations,
			int maxLifetimeMs,
			int maxIdenticalFitnesses,
			boolean verbose,
			boolean traceHistory,
			Reporter reporter
			){
		
        AlgorithmParameters algParameters = new AlgorithmParameters();
        algParameters.recombinationPerc = recombinationPerc;
        algParameters.mutationPerc = mutationPerc;
        algParameters.elitarism = elitarism;
        algParameters.initialSelectionNumber = initialSelectionNumber;
        algParameters.initialSelectionRandom = initialSelectionRandom;
        
        StopConditions stopConditions = new StopConditions();
        stopConditions.maxIterations = maxIterations;
        stopConditions.maxLifetimeMs = maxLifetimeMs;
        stopConditions.maxIdenticalFitnesses = maxIdenticalFitnesses;
        
        MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
        monitoringConfiguration.verbose = verbose;
        monitoringConfiguration.traceHistory = traceHistory;
        monitoringConfiguration.reporter = reporter;
        
        AlgorithmContext context = new AlgorithmContext();
        context.algorithmParameters = algParameters;
        context.algorithmParameters.stopConditions = stopConditions;
        context.monitoringConfiguration = monitoringConfiguration;
        
        return context;
	}
}
