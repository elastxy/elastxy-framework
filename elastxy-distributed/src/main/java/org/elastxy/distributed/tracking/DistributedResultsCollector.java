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

import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

/**
 * Interface for writing and retrieving distributed execution results 
 * over nodes, related to a single execution.
 * 
 * A more sophisticated implementation may use asynch messaging
 * system like Apache Kafka.
 * 
 * @author red
 *
 */
public interface DistributedResultsCollector {

	public void setup(DistributedAlgorithmContext context);

	/**
	 * Produce execution results and puts them 
	 * into the destination, based on implementation.
	 * 
	 * @param taskIdentifier
	 * @param stats
	 */
	public void produceResults(String taskIdentifier, MultiColonyExperimentStats stats);
	
	/**
	 * Consume execution results from the source,
	 * based on implementation.
	 * 
	 * @param taskIdentifier
	 * @param stats
	 */
    public MultiColonyExperimentStats consumeResults(String taskIdentifier);
}
