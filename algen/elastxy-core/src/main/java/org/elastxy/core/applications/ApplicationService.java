package org.elastxy.core.applications;

import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.context.ContextBuilder;
import org.elastxy.core.engine.core.Experiment;
import org.elastxy.core.engine.core.SingleColonyExperiment;
import org.elastxy.core.engine.operators.UniformlyDistributedSelector;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.StatsExperimentExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationService {
	private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);

	@Autowired private ContextBuilder benchmarkContextBuilder;
	
	@Autowired private AppComponentsLocator appComponentsLocator;


	public ExperimentStats executeBenchmark(String applicationName){
		AlgorithmContext context = benchmarkContextBuilder.build(applicationName, true);
		context.application.appName = applicationName;
		setupContext(context);
		Experiment e = new SingleColonyExperiment(context);
		e.run();
		ExperimentStats stats = e.getStats();
        return stats;
	}
	
	
	public ExperimentStats executeExperiment(AlgorithmContext context){
	 	setupContext(context);
	 	Experiment e = new SingleColonyExperiment(context);
        e.run();
        ExperimentStats stats = e.getStats();
        return stats;
	}
	
	
	public String executeAnalysis(AlgorithmContext context, int experiments){
	 	setupContext(context);
        StatsExperimentExecutor collector = new StatsExperimentExecutor(context, experiments);
        collector.run();
        String result = collector.print();
        return result;
	}
	
	
	public String executeTrialTest(AlgorithmContext context, int experiments){
	 	setupContext(context);
		
		// Trial parameters
		context.algorithmParameters.randomEvolution = true;
 		context.algorithmParameters.elitarism = false;
 		context.algorithmParameters.mutationPerc = 0.0;
 		context.algorithmParameters.recombinationPerc = 0.0;
 		context.algorithmParameters.crossoverPointRandom = false;
 		context.algorithmParameters.initialSelectionRandom = true;
 		
 		// Substitute Selector bean with uniform random pick
 		context.application.selector = new UniformlyDistributedSelector();
		context.application.selector.setup(context);

		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(context, experiments);
        collector.run();
        
        String result = collector.print();
        
		
		// TODOB-2: execute test trial beside normal analysis and compare results
//		StringBuffer result = new StringBuffer();
//		result
//		.append("\n\n*********** NORMAL TEST ***********")
//		.append(executeAnalysis(envFactory(), context, experiments))
//		.append("*********** RANDOM TEST ***********")
//		.append(executeTrialTest(envFactory(), context, experiments));
//		return result.toString();

        
        return result;
	}
	

	private void setupContext(AlgorithmContext context) {
		context.application = appComponentsLocator.get(context.application.appName);
		// in distributed context can be null
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context); 
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.recombinator.setup(context.algorithmParameters);
		context.application.targetBuilder.setup(context);
		context.application.envFactory.setup(context);
		
		// Distributed application
		if(context.application.multiColonyEnvFactory!=null) context.application.multiColonyEnvFactory.setup(context);
		if(context.application.distributedDatasetProvider!=null) context.application.distributedDatasetProvider.setup(context);
		if(context.application.singleColonyDatasetProvider!=null) context.application.singleColonyDatasetProvider.setup(context);
		if(context.application.distributedGenomaProvider!=null) context.application.distributedGenomaProvider.setup(context);
	}
	

}
