package org.elastxy.core.applications;

/**
 * Application stage determines type of experiment to configure.
 * @author red
 *
 */
public enum AppStage {
	EXPERIMENT(""), // standard experiment type with custom
					// configuration (when missing, defaults are applied)
	APPCHECK("appcheck"), // simple health check of application 
					// with a minimal configuration (defaults are applied)
	BENCHMARK("benchmark"); // benchmark execution for evaluating performance
							// against a reference configuration
	
	private String name;
	
	private AppStage(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
}
