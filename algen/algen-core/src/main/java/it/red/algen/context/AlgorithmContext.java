package it.red.algen.context;

import java.io.Serializable;

import it.red.algen.applications.components.AppComponents;
import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;

/**
 * Context of a single execution: application functions, parameters, 
 * custom input, monitoring conf.
 * 
 * All parameters attributes must be serializable, because 
 * they can be distributed to other executors in a cluster.
 * 
 * TODOM: check if AppComponents either should be Serializable... tradeoffs
 * 
 * @author red
 */
public class AlgorithmContext implements Serializable {
	
	/**
	 * Application components bootstrapped (copy created for this specific execution).
	 */
	public AppComponents application = new AppComponents();

	/**
	 * Specifics coming from an application for a given execution: custom parameters 
	 * to be used in the application specific component.
	 */
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
	
	/**
	 * Parameters to drive algorithm engine.
	 */
	public AlgorithmParameters algorithmParameters = new AlgorithmParameters();
	
	
	/**
	 * Configurations related to log, monitoring, etc.
	 */
	public MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
	
}
