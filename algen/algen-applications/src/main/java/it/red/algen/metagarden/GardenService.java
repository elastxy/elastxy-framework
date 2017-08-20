package it.red.algen.metagarden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.StandardMutator;
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
		context.incubator = new MegIncubator();

		context.fitnessCalculator = new MegFitnessCalculator();
		context.fitnessCalculator.setup(context.incubator);

		context.selector = new StandardSelector();
		context.selector.setup(context.parameters);
		
		context.mutator = new StandardMutator();
		
		context.recombinator = new SequenceRecombinator();
	}
	
}
