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
package org.elastxy.core.applications.components.factory;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elastxy.core.applications.components.AppRegister;
import org.elastxy.core.applications.components.ApplicationMetadata;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.support.JSONSupport;

/**
 * Retrieves Application metadata from classpath.
 * 
 * Default location is:
 * 
 * classpath:{applicationName}
 * 
 * @author red
 *
 */
public class ClasspathRegisterRaw implements AppRegister {
	private static final Logger logger = Logger.getLogger(ClasspathRegisterRaw.class);

	@Override
	public ApplicationMetadata find(String applicationName){
		String classpathResource = JSONSupport.checkClasspathResource(applicationName, "application.json");
		ApplicationMetadata result;
		try {
			result = (ApplicationMetadata)JSONSupport.readJSON(classpathResource, ApplicationMetadata.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return result;
	}

	@Override
	public Map<String, ApplicationMetadata> findAll() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}

	@Override
	public Map<String, ApplicationMetadata> register() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}

	@Override
	public Map<String, ApplicationMetadata> unregister() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}
}
