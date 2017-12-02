package it.red.algen.distributed.dataprovider;

import org.apache.spark.api.java.JavaRDD;

import it.red.algen.dataprovider.WorkingDataset;

public class RDDDistributedWorkingDataset<D> implements WorkingDataset {
	public JavaRDD<D> rdd;
}
