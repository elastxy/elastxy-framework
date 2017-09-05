package it.red.algen.metagarden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.SequenceMutator;
import it.red.algen.engine.StandardSelector;
import it.red.algen.service.AbstractApplicationService;

@Component
public class GardenService extends AbstractApplicationService{
//	private static Logger logger = LoggerFactory.getLogger(GardenService.class);

	@Autowired private MegEnvFactory envFactory;

	@Override
	protected String getApplicationName(){
		return "garden";
	}
	
	@Override
	protected EnvFactory envFactory(){
		return envFactory;
	}
	
	@Override
	protected void setupContext(AlgorithmContext context) {
		context.application.incubator = new MegIncubator();
		context.application.fitnessCalculator = new MegFitnessCalculator();
		context.application.fitnessCalculator.setup(context.application.incubator);
		context.application.selector = new StandardSelector();
		context.application.mutator = new SequenceMutator();
		context.application.recombinator = new SequenceRecombinator();
		
		context.application.selector.setup(context.parameters);
	}
	
}
