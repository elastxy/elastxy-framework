/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
