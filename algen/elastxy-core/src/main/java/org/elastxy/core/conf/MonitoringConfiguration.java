package org.elastxy.core.conf;

import java.io.Serializable;

import org.elastxy.core.tracking.Reporter;
import org.elastxy.core.tracking.SimpleLogger;


/**
 * Service bean to host any execution parameters useful for logging,
 * tracing, monitoring and controlling execution.
 * 
 * @author red
 *
 */
public class MonitoringConfiguration implements Serializable {
	public boolean verbose = DefaultMonitoringConfiguration.VERBOSE;
	public boolean showGraph = DefaultMonitoringConfiguration.SHOW_GRAPH;
	public boolean traceHistory = DefaultMonitoringConfiguration.TRACE_HISTORY;
	public transient Reporter reporter;
	public SimpleLogger logger = new SimpleLogger();
}
