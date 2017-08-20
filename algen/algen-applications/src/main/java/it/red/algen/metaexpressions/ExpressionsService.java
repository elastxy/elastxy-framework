package it.red.algen.metaexpressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.StandardMutator;
import it.red.algen.engine.StandardSelector;
import it.red.algen.service.AbstractApplicationService;
import it.red.algen.tracking.CSVReporter;

@Component
public class ExpressionsService extends AbstractApplicationService{
//	private static Logger logger = LoggerFactory.getLogger(ExpressionsService.class);

	@Autowired private MexEnvFactory envFactory;

	@Autowired private MexBenchmark benchmarkContextBuilder;
	

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
		context.incubator = new MexIncubator();

		context.fitnessCalculator = new MexFitnessCalculator();
		context.fitnessCalculator.setup(context.incubator);

		context.selector = new StandardSelector();
		context.selector.setup(context.parameters);
		
		context.mutator = new StandardMutator();
		
		context.recombinator = new SequenceRecombinator();

		context.monitoringConfiguration.reporter = new CSVReporter(MexApplication.STATS_DIR);
	}
	
}
