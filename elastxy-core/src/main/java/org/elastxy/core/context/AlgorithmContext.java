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
package org.elastxy.core.context;

import java.io.Serializable;
import java.util.Locale;

import org.elastxy.core.applications.components.AppComponents;
import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.conf.ApplicationSpecifics;
import org.elastxy.core.conf.DefaultConfiguration;
import org.elastxy.core.conf.DefaultMonitoringConfiguration;
import org.elastxy.core.conf.MonitoringConfiguration;

/**
 * Context of a single execution: application functions, parameters, 
 * custom input, monitoring conf.
 * 
 * More or less all parameters attributes should be serializable, because 
 * they can be distributed to other executors in a cluster.
 * 
 * TODO3-2: check if AppComponents either should be Serializable... tradeoffs
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
	 * Configurations related to log, monitoring, locale, etc.
	 */
	public MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
	
	
	/**
	 * Context related parameters to request: originator user, agent, language...
	 * 
	 */
	public RequestContext requestContext = new RequestContext();

}
