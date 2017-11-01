package it.red.algen.applications.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * ServiceLocator for retrieving AppComponents initialized at startup.
 * 
 * It's populated by the AppBootstrap and mainly used by AlgorithmContextBuilder.
 * 
 * @author red
 *
 */
@Component
public class AppComponentsLocator {
	private Map<String, AppComponents> appComponents = new HashMap<String, AppComponents>();
	
	/**
	 * Returns a copy of the AppComponents
	 * @param applicationName
	 * @return
	 */
	public AppComponents get(String applicationName){
		return appComponents.get(applicationName).copy();
	}

	public AppComponents put(String applicationName, AppComponents appComponents){
		return this.appComponents.put(applicationName, appComponents);
	}
}
