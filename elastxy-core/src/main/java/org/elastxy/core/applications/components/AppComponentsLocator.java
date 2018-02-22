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
package org.elastxy.core.applications.components;

import java.util.HashMap;
import java.util.Map;

import org.elastxy.core.conf.ConfigurationException;
import org.springframework.stereotype.Component;

/**
 * ServiceLocator for retrieving AppComponents initialized at startup.
 * 
 * It's populated by the AppBootstrap and mainly used by AlgorithmContextBuilder.
 * 
 * @author red
 *
 */
@Component
public class AppComponentsLocator {
	private Map<String, AppComponents> appComponents = new HashMap<String, AppComponents>();
	
	/**
	 * Returns a copy of the AppComponents
	 * @param applicationName
	 * @return
	 */
	public AppComponents get(String applicationName){
		AppComponents result = appComponents.get(applicationName);
		if(result==null){
			throw new ConfigurationException("Application "+applicationName+" not registered. Please register it putting its jars on classpath.");
		}
		return result.copy();
	}

	public AppComponents put(String applicationName, AppComponents appComponents){
		return this.appComponents.put(applicationName, appComponents);
	}
}
