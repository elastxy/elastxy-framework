package it.red.algen.metasudoku;

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
import it.red.algen.service.AbstractApplicationService;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.stats.StatsExperimentExecutor;

@Component
public class SudokuService extends AbstractApplicationService {
	private static Logger logger = LoggerFactory.getLogger(SudokuService.class);

	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private MesEnvFactory envFactory;

	@Autowired private PopulationFactory populationFactory;

	@Autowired private MesBenchmark benchmarkContextBuilder;
	
	@Autowired private AutowireCapableBeanFactory beanFactory;
	
	
	public ExperimentStats executeBenchmark(){
		return super.executeBenchmark(envFactory, benchmarkContextBuilder);
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
	

	public static void setupContext(AlgorithmContext context) {
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
