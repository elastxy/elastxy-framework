package it.red.algen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.controller.AlgenController;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.StandardMutator;
import it.red.algen.engine.StandardSelector;
import it.red.algen.engine.UniformlyDistributedSelector;
import it.red.algen.metasudoku.MesBenchmark;
import it.red.algen.metasudoku.MesEnvFactory;
import it.red.algen.metasudoku.MesFitnessCalculator;
import it.red.algen.metasudoku.MesGenomaProvider;
import it.red.algen.metasudoku.MesIncubator;
import it.red.algen.metasudoku.MesSolutionRenderer;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;

@Component
public class SudokuService {
	private static Logger logger = LoggerFactory.getLogger(AlgenController.class);

	@Autowired
	private ContextSupplier contextSupplier;
	

	@Autowired
	private MesEnvFactory envFactory;

	@Autowired
	private PopulationFactory populationFactory;

	@Autowired
	private MesGenomaProvider genomaProvider;
	
	@Autowired
	private MesBenchmark benchmark;
	
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	
	public ExperimentStats executeBenchmark(){

		// Context
		AlgorithmContext context = benchmark.build();
		contextSupplier.init(context);

		Gson gson = new Gson();
		String json = gson.toJson(context);
		logger.info(json);

		// Genoma
		context.mutator.setGenoma(genomaProvider.collect());

		// Experiment
		Experiment e = new Experiment(envFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();

        return stats;
	}
	
	
	public ExperimentStats executeExperiment(AlgorithmContext context){

		// Context
		contextSupplier.init(context);
	 	setupContext(context);

	 	Gson gson = new Gson();
		String json = gson.toJson(context);
		logger.info(json);

		// Genoma
		context.mutator.setGenoma(genomaProvider.collect());

		// Experiment
	 	Experiment e = new Experiment(envFactory);
	 	beanFactory.autowireBean(e);
	 	
        e.run();
        
        ExperimentStats stats = e.getStats();
        
        contextSupplier.destroy();
        
        return stats;
	}
	
	
	// TODOA: remove redundancy
	public String executeAnalysis(AlgorithmContext context, int experiments){

		// Context
		contextSupplier.init(context);
	 	setupContext(context);

	 	Gson gson = new Gson();
		String json = gson.toJson(context);
		logger.info(json);

		// Genoma
		context.mutator.setGenoma(genomaProvider.collect());
		
		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(this.envFactory, experiments);
        beanFactory.autowireBean(collector);
        
        collector.run();
        
        contextSupplier.destroy();
        
        String result = collector.print();
        return result;
	}
	
	
	public String executeTrialTest(AlgorithmContext context, int experiments){

		// Context
		contextSupplier.init(context);
	 	setupContext(context);

	 	Gson gson = new Gson();
		String json = gson.toJson(context);
		logger.info(json);
		
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
		context.incubator = new MesIncubator();

		context.fitnessCalculator = new MesFitnessCalculator();
		context.fitnessCalculator.setup(context.incubator);

		context.selector = new StandardSelector();
		context.selector.setup(context.parameters);
		
		context.mutator = new StandardMutator();
		
		context.recombinator = new SequenceRecombinator();

		context.monitoringConfiguration.solutionRenderer = new MesSolutionRenderer();
	}
	
}
