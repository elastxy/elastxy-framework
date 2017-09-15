package it.red.algen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import it.red.algen.components.AppComponentsLocator;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextBuilder;
import it.red.algen.context.ContextSupplier;
import it.red.algen.engine.UniformlyDistributedSelector;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;

@Component
public class ApplicationService {
//	private static Logger logger = LoggerFactory.getLogger(AbstractApplicationService.class);

	@Autowired private ContextSupplier contextSupplier;

	@Autowired private ContextBuilder benchmarkContextBuilder;
	
	@Autowired private AutowireCapableBeanFactory beanFactory;

	@Autowired private AppComponentsLocator appComponentsLocator;


	public ExperimentStats executeBenchmark(String applicationName){
		AlgorithmContext context = benchmarkContextBuilder.build(applicationName, true);
		context.application.name = applicationName;
		setupContext(context);
		contextSupplier.init(context);

		Experiment e = new Experiment(context.application.envFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        return stats;
	}
	
	
	public ExperimentStats executeExperiment(AlgorithmContext context){
	 	setupContext(context);
	 	contextSupplier.init(context);

	 	Experiment e = new Experiment(context.application.envFactory);
	 	beanFactory.autowireBean(e);
	 	
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        return stats;
	}
	
	
	public String executeAnalysis(AlgorithmContext context, int experiments){
	 	setupContext(context);
	 	contextSupplier.init(context);

        StatsExperimentExecutor collector = new StatsExperimentExecutor(context.application.envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
        String result = collector.print();
        return result;
	}
	
	
	public String executeTrialTest(AlgorithmContext context, int experiments){
		contextSupplier.init(context);
	 	setupContext(context);
		
		// Trial parameters
		context.parameters.randomEvolution = true;
 		context.parameters.elitarism = false;
 		context.parameters.mutationPerc = 0.0;
 		context.parameters.recombinationPerc = 0.0;
 		context.parameters.initialSelectionRandom = true;
 		
 		// Substitute Selector bean with uniform random pick
 		context.application.selector = new UniformlyDistributedSelector();
		context.application.selector.setup(context);

		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(context.application.envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
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
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.envFactory.setup(context);
	}
	

}
