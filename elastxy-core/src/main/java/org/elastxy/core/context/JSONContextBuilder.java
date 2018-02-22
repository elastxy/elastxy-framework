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

import java.io.IOException;
import java.net.URL;

import org.elastxy.core.applications.AppStage;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.support.JSONSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Returns the context given a JSON as a input
 * 
 * @author red
 *
 */
@Component
public class JSONContextBuilder implements ContextBuilder {
	private static Logger logger = LoggerFactory.getLogger(JSONContextBuilder.class);

	/**
	 * Retrieve from a classpath resource the json file for
	 * a benchmark or a standard experiment execution from:
	 * classpath:/{applicationName}/{fileName}.json
	 * 
	 * Where file names must be:
	 * - "appcheck" for simple check
	 * - "benchmark" for benchmark
	 * - "experiment" for experiment
	 * 
	 * If nothing's found, searches in:
	 * classpath:/app/{fileName}.json
	 * 
	 */
	@Override
	public AlgorithmContext build(String applicationName, AppStage appStage) {
		String classpathResource = JSONSupport.checkClasspathResource(applicationName, appStage.getName()+".json");
		AlgorithmContext result;
		try {
			result = (AlgorithmContext)JSONSupport.readJSON(classpathResource, AlgorithmContext.class);
			result.application.appName = applicationName;
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		
		return result;
	}
}
