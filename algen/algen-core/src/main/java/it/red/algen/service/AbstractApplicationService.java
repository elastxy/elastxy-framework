package it.red.algen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextBuilder;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.engine.UniformlyDistributedSelector;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;

public abstract class AbstractApplicationService {
	private static Logger logger = LoggerFactory.getLogger(AbstractApplicationService.class);

	@Autowired private ContextSupplier contextSupplier;

	@Autowired private ContextBuilder benchmarkContextBuilder;
	
	@Autowired private PopulationFactory populationFactory;

	@Autowired private  AutowireCapableBeanFactory beanFactory;
	

	public ExperimentStats executeBenchmark(){
        return executeBenchmark(envFactory());
	}
	
	public ExperimentStats executeExperiment(AlgorithmContext context){
        return executeExperiment(envFactory(), context);
	}
	
	public String executeAnalysis(AlgorithmContext context, int experiments){
        return executeAnalysis(envFactory(), context, experiments);
	}
	
	public String executeTrialTest(AlgorithmContext context, int experiments){
        return executeTrialTest(envFactory(), context, experiments);
	}
	
	protected ExperimentStats executeBenchmark(
			EnvFactory envFactory){
		AlgorithmContext context = benchmarkContextBuilder.build(getApplicationName(), true);
		setupContext(context);
		contextSupplier.init(context);

		Experiment e = new Experiment(envFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        return stats;
	}
	
	
	public ExperimentStats executeExperiment(EnvFactory envFactory, AlgorithmContext context){
		contextSupplier.init(context);
	 	setupContext(context);

	 	Experiment e = new Experiment(envFactory);
	 	beanFactory.autowireBean(e);
	 	
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        return stats;
	}
	
	
	public String executeAnalysis(EnvFactory envFactory, AlgorithmContext context, int experiments){
		contextSupplier.init(context);
	 	setupContext(context);

        StatsExperimentExecutor collector = new StatsExperimentExecutor(envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
        String result = collector.print();
        return result;
	}
	
	
	public String executeTrialTest(EnvFactory envFactory, AlgorithmContext context, int experiments){
		contextSupplier.init(context);
	 	setupContext(context);
		
		// Trial
		context.parameters.randomEvolution = true;
 		context.parameters.elitarism = false;
 		context.parameters.mutationPerc = 0.0;
 		context.parameters.recombinationPerc = 0.0;
 		context.parameters.initialSelectionRandom = true;
 		context.application.selector = new UniformlyDistributedSelector();
		context.application.selector.setup(context.parameters, populationFactory);

		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
        String result = collector.print();
        return result;
	}

	
	protected abstract String getApplicationName();

	protected abstract void setupContext(AlgorithmContext context);

	protected abstract EnvFactory envFactory();

}
