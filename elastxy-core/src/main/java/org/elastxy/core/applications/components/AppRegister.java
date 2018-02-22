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

import java.util.Map;

/**
 * Represents an Application metadata definition source:
 * where to physically retrieve the configurations (classpath, 
 * file-system, database..).
 * 
 * @author red
 *
 */
public interface AppRegister {
	

	/**
	 * Retrieves an application metadata
	 * @return
	 */
	public ApplicationMetadata find(String applicationName);

	/**
	 * Retrieves all application metadata
	 * @return
	 */
	public Map<String, ApplicationMetadata> findAll();

	
	/**
	 * Add a new application
	 * @return
	 */
	public Map<String, ApplicationMetadata> register();

	
	/**
	 * Remove an existing application from the register
	 * @return
	 */
	public Map<String, ApplicationMetadata> unregister();

}
