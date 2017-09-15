package it.red.algen.algofrigerator;

import org.springframework.stereotype.Component;

import it.red.algen.service.AbstractApplicationService;

@Component
public class AlgofrigeratorService extends AbstractApplicationService {

	@Override
	protected String getApplicationName(){
		return "algofrigerator";
	}

}
