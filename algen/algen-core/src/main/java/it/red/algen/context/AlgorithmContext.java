package it.red.algen.context;

import it.red.algen.components.AppComponents;
import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;

/**
 * Context of a single execution: application functions, parameters, custom input, monitoring conf.
 * 
 * @author red
 */
public class AlgorithmContext {
	
	/**
	 * Application components bootstrapped (copy created for this specific execution).
	 */
	public transient AppComponents application = new AppComponents();

	/**
	 * Specifics coming from an application for a given execution: custom parameters 
	 * to be used in the application specific component.
	 */
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
	
	/**
	 * Parameters to drive algorithm engine.
	 */
	public AlgorithmParameters parameters = new AlgorithmParameters();
	
	
	/**
	 * Configurations related to log, monitoring, etc.
	 */
	public MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
	
}
