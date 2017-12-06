package it.red.algen.d.metaexpressions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.log4j.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.distributed.dataprovider.BroadcastWorkingDataset;
import it.red.algen.distributed.dataprovider.DistributedDatasetProvider;
import it.red.algen.distributed.dataprovider.RDDDistributedWorkingDataset;
import it.red.algen.domain.experiment.Target;
import it.red.algen.metaexpressions.MexConstants;

/**
 * TODOD: bloccare le interfacce in ottica SDK!
 * @author red
 *
 */
public class MexdDistributedDatasetProvider implements DistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(MexdDistributedDatasetProvider.class);

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
		Long maxValue = context.applicationSpecifics.getParamLong(MexConstants.MAX_OPERAND_VALUE);
		int partitions = context.algorithmParameters.partitions;
		List<Long> range = LongStream.rangeClosed(-maxValue, maxValue).boxed().collect(Collectors.toList());
		
		// Partitioned RDD
		// TODOD: check performance when caching after count()
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
		// TODOD: check performance when caching after count()
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
