package it.red.algen.metasudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.StandardMutator;
import it.red.algen.engine.StandardSelector;
import it.red.algen.service.AbstractApplicationService;

@Component
public class SudokuService extends AbstractApplicationService {
//	private static Logger logger = LoggerFactory.getLogger(SudokuService.class);

	@Autowired private MesEnvFactory envFactory;

	@Autowired private MesBenchmark benchmarkContextBuilder;
	
	@Override
	protected EnvFactory envFactory(){
		return envFactory;
	}
	
	@Override
	protected BenchmarkContextBuilder benchmarkContextBuilder(){
		return benchmarkContextBuilder;
	}

	@Override
	protected void setupContext(AlgorithmContext context) {
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
