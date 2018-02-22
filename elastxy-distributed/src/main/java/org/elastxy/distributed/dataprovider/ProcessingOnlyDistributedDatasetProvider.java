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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.distributed.context.DistributedAlgorithmContext;


/**
 * This class provides behaviour for creating a fixed number
 * of partitions for processing-only experiment.
 * 
 * It means, it create N partitions containing only integer
 * numbers, to trigger the map partitions closure execution
 * across nodes.
 * 
 * @author red
 *
 */
public class ProcessingOnlyDistributedDatasetProvider implements DistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(ProcessingOnlyDistributedDatasetProvider.class);

	protected DistributedAlgorithmContext context;
	private RDDDistributedWorkingDataset<Integer> workingDataset;

	
	/**
	 * TODO2-4: put final on every blocked implementation
	 */
	public final void setup(AlgorithmContext context){
		this.context = (DistributedAlgorithmContext)context;
	}
	
	
	@Override
	public final WorkingDataset getWorkingDataset(){
		return workingDataset;
	}
	
	/**
	 * To be eventually implemented by extending classes
	 */
	@Override
	public void broadcast(){
	}

	/**
	 * To be eventually implemented by extending classes
	 */
	@Override
	public Map<String, BroadcastWorkingDataset> getBroadcastDatasets(){
		return null;
	}
	
	@Override
	public void collect() {
		int partitions = context.algorithmParameters.partitions;
		if(logger.isInfoEnabled()) logger.info("No data to collect for "+context.application.appName+". Only processing on "+partitions+" partitions.");

		List<Integer> range = IntStream.rangeClosed(0, partitions-1).boxed().collect(Collectors.toList());
		
		// Partitioned RDD
		workingDataset = new RDDDistributedWorkingDataset<Integer>();
		workingDataset.rdd = context.distributedContext.parallelize(range, partitions);
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}

	@Override
	public final void redistribute() {
		int partitions = context.algorithmParameters.partitions;
		if(logger.isDebugEnabled()) logger.debug(String.format("Coalescing %d partitions.", partitions));
		// TODO2-2: check performance of caching
		workingDataset.rdd = workingDataset.rdd.coalesce(partitions, true).cache();
//		workingDataset.numbersRDD.count();
	}
	
}
