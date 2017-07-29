package it.red.algen.context;

import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;
import it.red.algen.conf.OperatorsParameters;
import it.red.algen.conf.StopConditions;

public class AlgorithmContext {
	public OperatorsParameters parameters;
	public StopConditions stopConditions;
	public MonitoringConfiguration monitoringConfiguration;
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
}
