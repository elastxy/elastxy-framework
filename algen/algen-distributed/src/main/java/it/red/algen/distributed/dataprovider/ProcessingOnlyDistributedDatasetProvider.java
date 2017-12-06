package it.red.algen.distributed.dataprovider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.domain.experiment.Target;


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
	 * TODOA: put final on every blocked implementation
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
	public final void collect() {
		int partitions = context.algorithmParameters.partitions;
		if(logger.isInfoEnabled()) logger.info("No data to collect for "+context.application.name+". Only processing on "+partitions+" partitions.");
		
//		int halfPartitions = Math.floorDiv(partitions, 2) + 1;
//		List<Integer> range = IntStream.rangeClosed(-halfPartitions, partitions-halfPartitions).boxed().collect(Collectors.toList());

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
		// TODOD: check performance when caching after count()
		workingDataset.rdd = workingDataset.rdd.coalesce(partitions, true).cache();
//		workingDataset.numbersRDD.count();
	}
	
}