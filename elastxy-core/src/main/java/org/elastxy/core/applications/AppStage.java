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
package org.elastxy.core.applications;

/**
 * Application stage determines type of experiment to configure.
 * @author red
 *
 */
public enum AppStage {
	EXPERIMENT(""), // standard experiment type with custom
					// configuration (when missing, defaults are applied)
	APPCHECK("appcheck"), // simple health check of application 
					// with a minimal configuration (defaults are applied)
	BENCHMARK("benchmark"); // benchmark execution for evaluating performance
							// against a reference configuration
	
	private String name;
	
	private AppStage(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
}
