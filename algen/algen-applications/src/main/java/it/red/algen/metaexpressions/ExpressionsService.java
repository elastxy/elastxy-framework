package it.red.algen.metaexpressions;

import org.springframework.stereotype.Component;

import it.red.algen.service.AbstractApplicationService;

@Component
public class ExpressionsService extends AbstractApplicationService{

	@Override
	protected String getApplicationName(){
		return "expressions";
	}

}
