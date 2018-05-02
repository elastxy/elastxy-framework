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
package ${groupId}.multicolonyxo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.log4j.Logger;
import ${groupId}.multicolonyxo.AppConstants;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.dataprovider.BroadcastWorkingDataset;
import org.elastxy.distributed.dataprovider.DistributedDatasetProvider;
import org.elastxy.distributed.dataprovider.RDDDistributedWorkingDataset;

/**
 * TODO2-4: bloccare le interfacce in ottica SDK!
 * @author red
 *
 */
public class AppDistributedDatasetProvider implements DistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(AppDistributedDatasetProvider.class);

	private DistributedAlgorithmContext context;

	
	public void setup(AlgorithmContext context){
		this.context = (DistributedAlgorithmContext)context;
	}
	
	private RDDDistributedWorkingDataset<Long> workingDataset;
	
	@Override
	public WorkingDataset getWorkingDataset(){
		return workingDataset;
	}

	
	/**
	 * Collects all data in the current partition.
	 */
	@Override
	public void collect() {
		logger.info("Collecting data");
		
		// Raw data
		Long maxValue = context.applicationSpecifics.getParamLong(AppConstants.MAX_OPERAND_VALUE);
		int partitions = context.algorithmParameters.partitions;
		List<Long> range = LongStream.rangeClosed(-maxValue, maxValue).boxed().collect(Collectors.toList());
		
		// Partitioned RDD
		// TODO2-2: check performance of caching
		workingDataset = new RDDDistributedWorkingDataset<Long>();
		workingDataset.rdd = context.distributedContext.parallelize(range, partitions).cache();

		// Stats (and first execution...)
		if(logger.isDebugEnabled()){
			long count = workingDataset.rdd.count();
			logger.debug("   Collected data count="+count);
		}

		if(logger.isTraceEnabled()) {
			long min = workingDataset.rdd.min(Long::compare);
			long max = workingDataset.rdd.max(Long::compare);
			logger.debug("   Interval:["+min+","+max+"]");
		}
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}

	@Override
	public void redistribute() {
//	    if(logger.isDebugEnabled()) logger.debug(f"Repartition and cache partitions")
//	    inputData.numbersRDD = inputData.numbersRDD.repartition(partitions).cache()
	
		// Repartitions genoma
		int partitions = context.algorithmParameters.partitions;
		if(logger.isDebugEnabled()) logger.debug(String.format("Reshuffling data and spreading genetic material across %d colonies (partitions).", partitions));
		// TODO2-2: check performance of caching
		workingDataset.rdd = workingDataset.rdd.coalesce(partitions, true).cache();
//		workingDataset.rdd.count();
		
//	    if(logger.isDebugEnabled()) {
//	    	val count = inputData.numbersRDD.count()
//	      val max = inputData.numbersRDD.max()
//	      val min = inputData.numbersRDD.min()
//	      logger.debug(f"Repartition done and cached. Collected data count=$count Interval:[$min,$max]")
//	      Monitoring.printRDDGenoma(inputData.numbersRDD)
//	      Monitoring.printPartitionsGenoma(inputData.numbersRDD)
//	    }
//	    inputData 

	}

	@Override
	public Map<String, BroadcastWorkingDataset> getBroadcastDatasets() {
		return null;
	}


	@Override
	public void broadcast() {
	}
	
	
}
