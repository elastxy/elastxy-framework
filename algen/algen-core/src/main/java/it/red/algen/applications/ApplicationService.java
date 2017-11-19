package it.red.algen.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.applications.components.AppComponentsLocator;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextBuilder;
import it.red.algen.engine.core.Experiment;
import it.red.algen.engine.core.SingleTownExperiment;
import it.red.algen.engine.operators.UniformlyDistributedSelector;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;

@Component
public class ApplicationService {
	private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);

	@Autowired private ContextBuilder benchmarkContextBuilder;
	
	@Autowired private AppComponentsLocator appComponentsLocator;


	public ExperimentStats executeBenchmark(String applicationName){
		AlgorithmContext context = benchmarkContextBuilder.build(applicationName, true);
		context.application.name = applicationName;
		setupContext(context);
		Experiment e = new SingleTownExperiment(context);
		e.run();
		ExperimentStats stats = e.getStats();
        return stats;
	}
	
	
	public ExperimentStats executeExperiment(AlgorithmContext context){
	 	setupContext(context);
	 	Experiment e = new SingleTownExperiment(context);
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
 		context.algorithmParameters.initialSelectionRandom = true;
 		
 		// Substitute Selector bean with uniform random pick
 		context.application.selector = new UniformlyDistributedSelector();
		context.application.selector.setup(context);

		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(context, experiments);
        collector.run();
        
        String result = collector.print();
        
		
		// TODOM: execute test trial beside normal analysis to compare results
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
		context.application = appComponentsLocator.get(context.application.name);
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context);
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.envFactory.setup(context);
	}
	

}
