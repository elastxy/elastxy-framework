package org.elastxy.distributed.context;

import java.util.Properties;

import org.apache.spark.api.java.JavaSparkContext;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.distributed.tracking.DistributedResultsCollector;

public class DistributedAlgorithmContext extends AlgorithmContext {
	private static final long serialVersionUID = -3147739520028566916L;
	
	transient public JavaSparkContext distributedContext; // TODO3-4: decouple Context from Spark
	
	public boolean messagingEnabled;
	
	transient public String exchangePath;

	public Properties messagingProperties;

	transient public DistributedResultsCollector distributedResultsCollector;
}
