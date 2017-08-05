package it.red.algen.context;

import it.red.algen.conf.ApplicationSpecifics;
import it.red.algen.conf.MonitoringConfiguration;
import it.red.algen.conf.OperatorsParameters;
import it.red.algen.conf.StopConditions;
import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.Mutator;
import it.red.algen.engine.Recombinator;
import it.red.algen.engine.Selector;

// TODOA: move parameters into specific operators
public class AlgorithmContext {
	public transient FitnessCalculator fitnessCalculator;
	
	public OperatorsParameters parameters;
	public transient Selector selector;
	public transient Mutator mutator;
	public transient Recombinator recombinator;
	
	public StopConditions stopConditions;
	
	public MonitoringConfiguration monitoringConfiguration;
	public ApplicationSpecifics applicationSpecifics = new ApplicationSpecifics();
}
