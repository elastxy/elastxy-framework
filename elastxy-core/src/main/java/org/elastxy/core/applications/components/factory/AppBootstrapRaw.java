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

import org.apache.log4j.Logger;
import org.elastxy.core.applications.components.ApplicationMetadata;
import org.elastxy.core.applications.components.AppComponents;
import org.elastxy.core.applications.components.AppComponentsBuilder;
import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.applications.components.AppRegister;

/**
 * Builds up applications component and makes them available,
 * starting from a application register.
 * 
 * NO SPRING Version to be used where Spring is missing
 * 
 * @author red
 */
public class AppBootstrapRaw {
	private static Logger logger = Logger.getLogger(AppBootstrapRaw.class);

	
	/**
	 * Starts an ElastXY registered application
	 */
	public AppComponentsLocator boot(String applicationName){
		logger.info("Bootstrapping ElastXY local...");
		
		// Gets all registered apps from Register
		if(logger.isDebugEnabled()) logger.debug("Finding registered application '"+applicationName+"'");
		AppRegister register = new ClasspathRegisterRaw();
		ApplicationMetadata app = register.find(applicationName);
		if(logger.isDebugEnabled()) logger.debug("Application found: "+app);
		
		
		// Build progressively all the applications
		if(logger.isDebugEnabled()) logger.debug(">> Bootstrapping application '"+applicationName+"'");
		
		if(logger.isDebugEnabled()) logger.debug("   Building components..");
		AppComponentsBuilder builder = new AppComponentsBuilder();
		AppComponents appComponents = builder.construct(app);
	
		if(logger.isDebugEnabled()) logger.debug("   Wiring components..");
		appComponents = builder.wire(appComponents);
			
		if(logger.isDebugEnabled()) logger.debug("   Initializing components..");
		appComponents = builder.init(appComponents);
		
		AppComponentsLocator locator = new AppComponentsLocator();
		locator.put(applicationName, appComponents);
		if(logger.isDebugEnabled()) logger.debug("   Welcome to '"+applicationName+"' application! <!!!>o");
		
		if(logger.isDebugEnabled()) logger.debug("Bootstrap ElastXY local DONE.");
		return locator;
	}
	
	
}
