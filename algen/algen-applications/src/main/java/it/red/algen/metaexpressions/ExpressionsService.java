package it.red.algen.metaexpressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.components.AppComponentsLocator;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.service.AbstractApplicationService;

@Component
public class ExpressionsService extends AbstractApplicationService{
//	private static Logger logger = LoggerFactory.getLogger(ExpressionsService.class);

	@Autowired private MexEnvFactory envFactory;

	@Autowired private AppComponentsLocator appComponentsLocator;
	
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
		context.application = appComponentsLocator.get(getApplicationName());
		context.application.selector.setup(context.parameters);
	}
	
}
