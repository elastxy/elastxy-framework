package it.red.algen.metagarden;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.StandardMutator;
import it.red.algen.engine.StandardSelector;
import it.red.algen.engine.UniformlyDistributedSelector;
import it.red.algen.metaexpressions.MexApplication;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;
import it.red.algen.tracking.CSVReporter;

@Component
public class GardenService {
	private static Logger logger = LoggerFactory.getLogger(GardenService.class);

	@Autowired private ContextSupplier contextSupplier;

	@Autowired private MegEnvFactory envFactory;

	@Autowired private PopulationFactory populationFactory;

	@Autowired private MegBenchmark benchmark;
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	
	public ExperimentStats executeBenchmark(){
		AlgorithmContext context = benchmark.build();
		contextSupplier.init(context);

		Experiment e = new Experiment(envFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        return stats;
	}
	
	
	public ExperimentStats executeExperiment(AlgorithmContext context){
		contextSupplier.init(context);
	 	setupContext(context);

	 	Experiment e = new Experiment(envFactory);
	 	beanFactory.autowireBean(e);
	 	
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        return stats;
	}
	
	
	// TODOA: remove redundancy
	public String executeAnalysis(AlgorithmContext context, int experiments){
		contextSupplier.init(context);
		setupContext(context);

        StatsExperimentExecutor collector = new StatsExperimentExecutor(this.envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
        String result = collector.print();
        return result;
	}
	
	
	public String executeTrialTest(AlgorithmContext context, int experiments){
		contextSupplier.init(context);
	 	setupContext(context);

		// Trial
		context.parameters.randomEvolution = true;
 		context.parameters.elitarism = false;
 		context.parameters.mutationPerc = 0.0;
 		context.parameters.recombinationPerc = 0.0;
 		context.parameters.initialSelectionRandom = true;
 		context.selector = new UniformlyDistributedSelector();
		context.selector.setup(context.parameters, populationFactory);

		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(this.envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
        String result = collector.print();
        return result;
	}
	

	private void setupContext(AlgorithmContext context) {
		context.incubator = new MegIncubator();

		context.fitnessCalculator = new MegFitnessCalculator();
		context.fitnessCalculator.setup(context.incubator, null);

		context.selector = new StandardSelector();
		context.selector.setup(context.parameters);
		
		context.mutator = new StandardMutator();
		
		context.recombinator = new SequenceRecombinator();

		context.monitoringConfiguration.reporter = new CSVReporter(MexApplication.STATS_DIR);
	}
	
}