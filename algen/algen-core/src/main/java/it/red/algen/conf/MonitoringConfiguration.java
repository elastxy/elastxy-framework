package it.red.algen.conf;

import it.red.algen.tracking.Logger;
import it.red.algen.tracking.Reporter;
import it.red.algen.tracking.SimpleLogger;

public class MonitoringConfiguration {
	public boolean verbose = DefaultMonitoringConfiguration.VERBOSE;
	public transient Reporter reporter;
	public transient Logger logger = new SimpleLogger();
}
