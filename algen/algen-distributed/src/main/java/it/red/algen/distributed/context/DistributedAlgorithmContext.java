package it.red.algen.distributed.context;

import org.apache.spark.api.java.JavaSparkContext;

import it.red.algen.context.AlgorithmContext;

public class DistributedAlgorithmContext extends AlgorithmContext {
	transient public JavaSparkContext distributedContext; // TODOM: decouple Context from Spark
}
