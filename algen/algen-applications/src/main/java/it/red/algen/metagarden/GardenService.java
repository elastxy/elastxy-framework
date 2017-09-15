package it.red.algen.metagarden;

import org.springframework.stereotype.Component;

import it.red.algen.service.AbstractApplicationService;

@Component
public class GardenService extends AbstractApplicationService{

	@Override
	protected String getApplicationName(){
		return "garden";
	}
	
}
