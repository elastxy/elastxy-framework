package it.red.algen.d.metasudoku;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.distributed.dataprovider.DistributedDatasetProvider;
import it.red.algen.distributed.dataprovider.RDDDistributedWorkingDataset;
import it.red.algen.domain.experiment.Target;

/**
 * TODOD: bloccare le interfacce in ottica SDK!
 * @author red
 *
 */
public class MesdDistributedDatasetProvider implements DistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(MesdDistributedDatasetProvider.class);

	private DistributedAlgorithmContext context;
	private RDDDistributedWorkingDataset<Integer> workingDataset;

	
	public void setup(AlgorithmContext context){
		this.context = (DistributedAlgorithmContext)context;
	}
	
	
	@Override
	public WorkingDataset getWorkingDataset(){
		return workingDataset;
	}
	
	/**
	 * For Sudoku only creating partitions is needed, nothing more.
	 * TODOD: if works generic dataset for processing-only
	 */
	@Override
	public void collect() {
		logger.info("No data to collect for Sudoku. Only elaborations.");
		
		int partitions = context.algorithmParameters.partitions;
		int halfPartitions = partitions / 2;
		List<Integer> range = IntStream.rangeClosed(-halfPartitions, partitions-halfPartitions).boxed().collect(Collectors.toList());
		
		// Partitioned RDD
		workingDataset = new RDDDistributedWorkingDataset<Integer>();
		workingDataset.rdd = context.distributedContext.parallelize(range, partitions);
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}

	@Override
	public void redistribute() {
		int partitions = context.algorithmParameters.partitions;
		if(logger.isDebugEnabled()) logger.debug(String.format("Coalescing %d partitions.", partitions));
		// TODOD: check performance when caching after count()
		workingDataset.rdd = workingDataset.rdd.coalesce(partitions, true).cache();
//		workingDataset.numbersRDD.count();
	}
	
	
}
