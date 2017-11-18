package it.red.algen.d.metaexpressions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.DatasetProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.domain.experiment.Target;
import it.red.algen.metaexpressions.MexConstants;

public class MexdDatasetProvider implements DatasetProvider {
	private static Logger logger = Logger.getLogger(MexdDatasetProvider.class);

	private DistributedAlgorithmContext context;

	
	public void setup(AlgorithmContext context){
		this.context = (DistributedAlgorithmContext)context;
	}
	
	private MexdWorkingDataset workingDataset;
	
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
		List<Long> range = LongStream.rangeClosed(-maxValue*maxValue, +maxValue*maxValue).boxed().collect(Collectors.toList());
		JavaRDD<Long> numbersRDD = context.distributedContext.parallelize(range, 4);

		// Spark numbers RDD
		workingDataset = new MexdWorkingDataset();
		workingDataset.numbers = numbersRDD.cache();
		
		// Stats (and first execution...)
		long count = workingDataset.numbers.count();
		logger.info("   Collected data count="+count);
		if(logger.isTraceEnabled()) {
			long min = workingDataset.numbers.min(Long::compare);
			long max = workingDataset.numbers.max(Long::compare);
			logger.debug("   Interval:["+min+","+max+"]");
		}
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}
	
	
}
