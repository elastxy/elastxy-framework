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
package org.elastxy.distributed.tracking;

import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.DefaultResultsRenderer;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

public class DistributedResultsRenderer extends DefaultResultsRenderer{



	@Override
	public ClientFriendlyResults renderFriendly(ExperimentStats stats) {
		MultiColonyExperimentStats mstats = (MultiColonyExperimentStats)stats;
		ClientFriendlyResults result = new ClientFriendlyResults();
		result.goalReached = mstats.targetReached;
		
		result.accuracy = mstats.bestMatch.getFitness().getValue().doubleValue();

		result.iterationsNumber = mstats.eras;
		result.totalExecutionTimeMs = mstats.executionTimeMs;
		
		// default representation is toString()
		result.stringResult = friendlySolutionRenderer.render(mstats.bestMatch).toString();
		result.binaryResult = null;
		result.notes = null;
		return result;
	}
}
