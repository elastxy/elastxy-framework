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
package org.elastxy.web.renderer;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.web.controller.ExperimentResponse;

/**
 * Interface defining the contract for rendering
 * a generic response to clients.
 * 
 * @author red
 *
 */
public interface ExperimentResponseRenderer {

	/**
	 * Render successful experiment stats.
	 * @param stats
	 * @return
	 */
	public ExperimentResponse render(AlgorithmContext context, ExperimentStats stats);

	
	/**
	 * Render generic successful experiment details when no stats 
	 * are provided (e.g. analysis services).
	 * 
	 * @param stats
	 * @return
	 */
	public ExperimentResponse render(AlgorithmContext context, String content);
	
	
	/**
	 * Render error experiment stats.
	 * @param stats
	 * @return
	 */
	public ExperimentResponse render(AlgorithmContext context, Throwable t);
}
