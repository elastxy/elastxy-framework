package it.red.algen.conf;

import java.io.Serializable;

import it.red.algen.tracking.Logger;
import it.red.algen.tracking.Reporter;
import it.red.algen.tracking.SimpleLogger;

public class MonitoringConfiguration implements Serializable {
	public boolean verbose = DefaultMonitoringConfiguration.VERBOSE;
	public boolean showGraph = DefaultMonitoringConfiguration.SHOW_GRAPH;
	public boolean traceHistory = DefaultMonitoringConfiguration.TRACE_HISTORY;
	public transient Reporter reporter;
	public SimpleLogger logger = new SimpleLogger();
}
