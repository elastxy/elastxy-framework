package it.red.algen.d.metaexpressions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.distributed.dataprovider.DistributedDatasetProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.metaexpressions.MexConstants;

public class MexdDistributedDatasetProvider implements DistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(MexdDistributedDatasetProvider.class);

	private DistributedAlgorithmContext context;

	
	public void setup(AlgorithmContext context){
		this.context = (DistributedAlgorithmContext)context;
	}
	
	private MexdDistributedWorkingDataset workingDataset;
	
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
		List<Long> range = LongStream.rangeClosed(-maxValue*maxValue, +maxValue*maxValue).boxed().collect(Collectors.toList());
		
		// Partitioned RDD
		JavaRDD<Long> numbersRDD = context.distributedContext.parallelize(range, partitions);

		// Spark numbers RDD
		workingDataset = new MexdDistributedWorkingDataset();
		workingDataset.numbersRDD = numbersRDD.cache();
		
		// Stats (and first execution...)
		long count = workingDataset.numbersRDD.count();
		logger.info("   Collected data count="+count);
		if(logger.isTraceEnabled()) {
			long min = workingDataset.numbersRDD.min(Long::compare);
			long max = workingDataset.numbersRDD.max(Long::compare);
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
		workingDataset.numbersRDD = workingDataset.numbersRDD.coalesce(partitions, true).cache();
	
	    //inputData.numbersRDD.collect()
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
	
	
}
