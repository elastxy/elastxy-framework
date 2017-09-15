package it.red.algen.algofrigerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.service.AbstractApplicationService;

@Component
public class AlgofrigeratorService extends AbstractApplicationService{
//	private static Logger logger = LoggerFactory.getLogger(ExpressionsService.class);

	@Autowired private MefEnvFactory envFactory;
	
	@Override
	protected String getApplicationName(){
		return "algofrigerator";
	}

	@Override
	protected EnvFactory envFactory(){
		return envFactory;
	}
	
}
