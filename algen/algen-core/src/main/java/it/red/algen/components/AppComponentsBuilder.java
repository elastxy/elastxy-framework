package it.red.algen.components;

import org.springframework.stereotype.Component;

/**
 * Builds up all the ApplicationComponents, based on 
 * AlgenApplication metadata definition.
 * 
 * @author red
 *
 */
@Component
public class AppComponentsBuilder {

	
	/**
	 * Creates the ApplicationComponents from their definition
	 */
	public void construct(){
		throw new UnsupportedOperationException("NYI");
	}
	
	
	/**
	 * Wires ApplicationComponents up so that they could collaborate
	 */
	public void wire(){
		throw new UnsupportedOperationException("NYI");
	}
	
	
	/**
	 * Initialize the ApplicationComponents with BootstrapAware
	 */
	public void init(){
		throw new UnsupportedOperationException("NYI");
	}
	
}
