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

import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Builds up applications component and makes them available,
 * starting from a application register.
 * 
 * @author red
 */
@Component
public class AppBootstrap {
	private static Logger logger = Logger.getLogger(AppBootstrap.class);

	@Autowired private AppRegister register;
	
	@Autowired private AppComponentsBuilder builder;
	
	@Autowired private AppComponentsLocator locator;
	
	
	/**
	 * Starts an ElastXY registered applications
	 */
	@PostConstruct
	public void boot(){
		logger.info("Bootstrapping ElastXY..");
		
		// Gets all registered apps from Register
		logger.info("Finding all registered applications");
		Map<String, ApplicationMetadata> apps = register.findAll();
		logger.info("Applications found: "+apps.keySet());
		
		
		// Build progressively all the applications
		Iterator<String> itApps = apps.keySet().iterator();
		while(itApps.hasNext()){
			String applicationName = itApps.next();
			
			logger.info(">> Bootstrapping application '"+applicationName+"'");
			ApplicationMetadata app = apps.get(applicationName);
		
			logger.info("   Building components..");
			AppComponents appComponents = builder.construct(app);
	
			logger.info("   Wiring components..");
			appComponents = builder.wire(appComponents);
			
			logger.info("   Initializing components..");
			appComponents = builder.init(appComponents);
			
			locator.put(applicationName, appComponents);
			logger.info("   Welcome to '"+applicationName+"' application! <!!!>o");
		}
		
		logger.info("Bootstrap ElastXY DONE.");
	}
	
	
}
