package it.red.algen.metagarden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.components.AppComponentsLocator;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.service.AbstractApplicationService;

@Component
public class GardenService extends AbstractApplicationService{
//	private static Logger logger = LoggerFactory.getLogger(GardenService.class);

	@Autowired private MegEnvFactory envFactory;

	@Autowired private AppComponentsLocator appComponentsLocator;

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
		context.application = appComponentsLocator.get(getApplicationName());
		context.application.selector.setup(context.parameters);
	}
	
}
