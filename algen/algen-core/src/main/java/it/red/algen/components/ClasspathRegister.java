package it.red.algen.components;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Retrieves Application metadata from classpath.
 * 
 * Default location is:
 * 
 * classpath:{applicationName}
 * 
 * @author red
 *
 */
@Component
public class ClasspathRegister implements AppRegister {

	@Override
	public Map<String, AlgenApplication> findAll() {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public Map<String, AlgenApplication> register() {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public Map<String, AlgenApplication> unregister() {
		throw new UnsupportedOperationException("NYI");
	}

}
