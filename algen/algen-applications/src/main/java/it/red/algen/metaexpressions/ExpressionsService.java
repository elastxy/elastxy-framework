package it.red.algen.metaexpressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
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

	
	@Override
	protected String getApplicationName(){
		return "expressions";
	}

	@Override
	protected EnvFactory envFactory(){
		return envFactory;
	}

	@Override
	protected void setupContext(AlgorithmContext context) {
		context.application.incubator = new MexIncubator();

		context.application.fitnessCalculator = new MexFitnessCalculator();
		context.application.fitnessCalculator.setup(context.application.incubator);

		context.application.selector = new StandardSelector();
		context.application.selector.setup(context.parameters);
		
		context.application.mutator = new StandardMutator();
		
		context.application.recombinator = new SequenceRecombinator();

		context.monitoringConfiguration.reporter = new CSVReporter(MexApplication.STATS_DIR);
	}
	
}
