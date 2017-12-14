package org.elastxy.distributed.context;

import org.apache.spark.api.java.JavaSparkContext;
import org.elastxy.core.context.AlgorithmContext;

public class DistributedAlgorithmContext extends AlgorithmContext {
	private static final long serialVersionUID = -3147739520028566916L;
	
	transient public JavaSparkContext distributedContext; // TODOB-4: decouple Context from Spark
}
