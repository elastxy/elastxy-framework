package it.red.algen.context;

import it.red.algen.components.AppComponents;
import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;
import it.red.algen.conf.StopConditions;

public class AlgorithmContext {
	
	public transient AppComponents application = new AppComponents(); // TODOA: by bootstrap

	public AlgorithmParameters parameters;  // TODOA: rename to AppParameters
	
	public StopConditions stopConditions; // TODOA: move under AppParameters
	
	public MonitoringConfiguration monitoringConfiguration; // TODOA: rename to EngineConfigurations
	
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
}
