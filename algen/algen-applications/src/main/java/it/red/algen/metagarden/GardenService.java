package it.red.algen.metagarden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.EnvFactory;
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
	
}
