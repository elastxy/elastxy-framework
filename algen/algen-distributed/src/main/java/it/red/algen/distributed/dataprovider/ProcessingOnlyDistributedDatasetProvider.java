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
	 * TODOA-4: put final on every blocked implementation
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

		// TODOM-8: map partition over any genes, specified by metadata (e.g. map on operators on mexd)
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
		// TODOA-2: check performance of caching
		workingDataset.rdd = workingDataset.rdd.coalesce(partitions, true).cache();
//		workingDataset.numbersRDD.count();
	}
	
}