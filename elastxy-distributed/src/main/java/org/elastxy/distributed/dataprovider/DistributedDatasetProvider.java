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
package org.elastxy.distributed.dataprovider;

import java.util.Map;

import org.elastxy.core.dataprovider.DatasetProvider;

/**
 * DatasetProvider implementation capable of collecting
 * raw input data into an RDD and random repartitioning
 * (coalesce) data across nodes.
 * 
 * TODO3-8: input data in streaming
 *  
 * After this operation, genetic material is shared
 * by spreading Genoma either (within same partition),
 * in a normal evolution.
 * 
 * It also offer capability to broadcast and maintain
 * reference to broadcasted set of data used in the nodes to operate.
 * E.g. the garden database.
 * 
 * @author red
 *
 */
public interface DistributedDatasetProvider extends DatasetProvider {

	/**
	 * Redistributed data across colonies (nodes of a cluster).
	 * 
	 * It's a needed pre-requisite for spreading genetic material
	 * around to increment diversity and improving global fitness.
	 */
	public void redistribute();
	
	/**
	 * Create broadcast datasets for spreading any needed data 
	 * across nodes, and assign them to a distributed dataset.
	 * 
	 * Eventually, is based on already shrinked data from a local
	 * working dataset already created and shrinked from MultiColonyEnvFactory.
	 */
	public void broadcast();
	
	/**
	 * Returns the datasets created by broadcasting data to colonies.
	 * @return
	 */
	public Map<String, BroadcastWorkingDataset> getBroadcastDatasets();
}
