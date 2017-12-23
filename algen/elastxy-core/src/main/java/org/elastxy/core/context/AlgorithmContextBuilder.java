package org.elastxy.core.context;

import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.conf.MonitoringConfiguration;
import org.elastxy.core.conf.StopConditions;
import org.elastxy.core.tracking.Reporter;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmContextBuilder {

	public AlgorithmContext build(
			long initialSelectionNumber,
			boolean initialSelectionRandom,
			boolean crossoverPointRandom,
			double recombinationPerc,
			double mutationPerc,
			boolean elitarism,
			int maxGenerations,
			int maxLifetimeMs,
			int maxIdenticalFitnesses,
			boolean verbose,
			boolean showGraph,
			boolean traceHistory,
			Reporter reporter
			){
		
        AlgorithmParameters algParameters = new AlgorithmParameters();
        algParameters.recombinationPerc = recombinationPerc;
        algParameters.crossoverPointRandom = crossoverPointRandom;
        algParameters.mutationPerc = mutationPerc;
        algParameters.elitarism = elitarism;
        algParameters.initialSelectionNumber = initialSelectionNumber;
        algParameters.initialSelectionRandom = initialSelectionRandom;
        
        StopConditions stopConditions = new StopConditions();
        stopConditions.maxGenerations = maxGenerations;
        stopConditions.maxLifetimeMs = maxLifetimeMs;
        stopConditions.maxIdenticalFitnesses = maxIdenticalFitnesses;
        
        MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
        monitoringConfiguration.verbose = verbose;
        monitoringConfiguration.showGraph = showGraph;
        monitoringConfiguration.traceHistory = traceHistory;
        monitoringConfiguration.reporter = reporter;
        
        AlgorithmContext context = new AlgorithmContext();
        context.algorithmParameters = algParameters;
        context.algorithmParameters.stopConditions = stopConditions;
        context.monitoringConfiguration = monitoringConfiguration;
        
        return context;
	}
}
