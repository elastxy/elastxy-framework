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

import org.elastxy.core.applications.AppStage;

/**
 * Convenient interface for building up an AlgorithmContext,
 * given its simplest coordinates
 * @author red
 */
public interface ContextBuilder {
	
	/**
	 * Build an AlgorithmContext given application name and stage.
	 * @param application
	 * @param benchmark
	 * @return
	 */
	public AlgorithmContext build(String applicationName, AppStage appStage);

}
