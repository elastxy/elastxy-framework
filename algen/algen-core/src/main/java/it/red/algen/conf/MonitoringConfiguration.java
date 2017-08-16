package it.red.algen.conf;

import it.red.algen.tracking.DefaultSolutionRenderer;
import it.red.algen.tracking.Logger;
import it.red.algen.tracking.Reporter;
import it.red.algen.tracking.SimpleLogger;
import it.red.algen.tracking.SolutionRenderer;

public class MonitoringConfiguration {
	public boolean verbose = DefaultMonitoringConfiguration.VERBOSE;
	public SolutionRenderer solutionRenderer = new DefaultSolutionRenderer();
	public boolean traceHistory = DefaultMonitoringConfiguration.TRACE_HISTORY;
	public transient Reporter reporter;
	public transient Logger logger = new SimpleLogger();
}
