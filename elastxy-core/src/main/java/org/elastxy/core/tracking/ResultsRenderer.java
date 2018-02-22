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
package org.elastxy.core.tracking;

import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.TechnicalResults;

/**
 * An interface for providing both technical and user friendly
 * experiment outcomes rendering.
 * 
 * Solution renderer can be different in either cases.
 * If only one is specified, by default that's used.
 * 
 * @author red
 *
 */
public interface ResultsRenderer {
	
//	public void setup(AlgorithmContext context);

	public void setTechieSolutionRenderer(SolutionRenderer solutionRenderer);

	public void setFriendlySolutionRenderer(SolutionRenderer solutionRenderer);
	
	public TechnicalResults renderTechie(ExperimentStats stats);
	
	public ClientFriendlyResults renderFriendly(ExperimentStats stats);

}
