package it.red.algen.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Builds up applications component and makes them available,
 * starting from a application register.
 * 
 * @author red
 */
@Component
public class AppBootstrap {

	@Autowired
	private AppRegister register;
	
	public AppBootstrap(AppRegister register){
		this.register= register;
	}
	
	/**
	 * Starts an Algen registered applications
	 */
	public AppComponents boot(){
		throw new UnsupportedOperationException("NYI");
	}
	
	
}
