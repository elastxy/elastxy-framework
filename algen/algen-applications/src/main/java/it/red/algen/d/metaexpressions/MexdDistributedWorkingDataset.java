package it.red.algen.d.metaexpressions;

import org.apache.spark.api.java.JavaRDD;

import it.red.algen.dataprovider.WorkingDataset;

/**
 * Holds the reference of the original RDD within
 * the Driver program.
 * 
 * @author red
 *
 */
public class MexdDistributedWorkingDataset implements WorkingDataset {
	public JavaRDD<Long> numbersRDD;
}
