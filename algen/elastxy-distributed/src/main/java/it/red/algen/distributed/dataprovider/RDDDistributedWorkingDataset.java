package it.red.algen.distributed.dataprovider;

import org.apache.spark.api.java.JavaRDD;

public class RDDDistributedWorkingDataset<D> implements DistributedWorkingDataset {
	public JavaRDD<D> rdd;
}
