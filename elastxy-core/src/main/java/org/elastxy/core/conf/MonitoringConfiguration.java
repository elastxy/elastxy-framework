/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
