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
package org.elastxy.web.distributed;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.appsupport.ElastXYDriverApplication;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;
import org.elastxy.distributed.tracking.DistributedResultsCollector;
import org.elastxy.distributed.tracking.StandardDistributedResultsCollector;
import org.elastxy.distributed.tracking.kafka.KafkaDistributedResultsCollector;
import org.elastxy.web.controller.ExperimentResponse;
import org.elastxy.web.renderer.InternalExperimentResponseRenderer;
import org.elastxy.web.renderer.WebExperimentResponseRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:distributed.properties")
public class DistributedApplicationService {
	private static Logger logger = LoggerFactory.getLogger(DistributedApplicationService.class);

	
	@Value("${messaging.enabled}")
	private Boolean messagingEnabled;
	
	@Autowired private AppComponentsLocator appComponentsLocator;
	
	@Autowired private ApplicationsSparkConfig applicationsSparkConfig;
	
	@Autowired private WebExperimentResponseRenderer webRenderer;
	@Autowired private InternalExperimentResponseRenderer intRenderer;
	
	
	
	public ExperimentResponse executeDistributedLocal(DistributedAlgorithmContext context) throws Exception {
		
		// Configure
		SparkTaskConfig taskConfig = applicationsSparkConfig.getTaskConfig(context.application.appName);
		setupContext(context, taskConfig.webappInboundPath);

		// Setup configurations
    	logger.info("Local Service Task Configuration");
    	logger.info(taskConfig.toString());

		String master = "local[*]";
		byte[] contextBytes = JSONSupport.writeJSONString(context).getBytes(); 
		String contextAsString = Base64.getEncoder().encodeToString(contextBytes);
		
		// Execute job
		String[] params = new String[]{
				context.application.appName, 
				taskConfig.taskIdentifier, 
				taskConfig.sparkHome, 
				taskConfig.driverInboundPath, 
				taskConfig.driverOutboundPath, 
				master, 
				contextAsString};
    	logger.info("Submitting job locally with params: "+Arrays.asList(params));
		ElastXYDriverApplication.main(params);

		// Export results
		DistributedResultsCollector collector = createResultsCollector(context);
		MultiColonyExperimentStats stats = collector.consumeResults(taskConfig.taskIdentifier);
    	ExperimentResponse response = res(context.requestContext.webRequest, context, stats);
    	
    	return response;
	}
	
	
	
	public ExperimentResponse executeDistributedCluster(DistributedAlgorithmContext context) throws Exception {

		// Configure
		SparkTaskConfig taskConfig = applicationsSparkConfig.getTaskConfig(context.application.appName);
		setupContext(context, taskConfig.webappInboundPath);
    	SparkTaskExecutor executor = new SparkTaskExecutor();
    	
    	// Setup configurations
    	logger.info("Cluster Service Task Configuration");
    	logger.info(taskConfig.toString());
    	
    	// Execute job
    	String driverStatus = executor.runDistributed(taskConfig, context);

    	// Export results
    	context.distributedResultsCollector = createResultsCollector(context);
		MultiColonyExperimentStats stats = context.distributedResultsCollector.consumeResults(taskConfig.taskIdentifier);
    	ExperimentResponse response = res(context.requestContext.webRequest, context, stats);
    	
    	return response;
	}


    // TODO3-1: remove duplication on ExperimentResponse builder
	private ExperimentResponse res(boolean webRequest, AlgorithmContext context, ExperimentStats stats){
		return webRequest ? webRenderer.render(context, stats) : intRenderer.render(context, stats);
	}

	private ExperimentResponse res(boolean webRequest, AlgorithmContext context, String content){
		return webRequest ? webRenderer.render(context, content) : intRenderer.render(context, content);
	}
	
	
	private void setupContext(DistributedAlgorithmContext context, String exchangePath) throws Exception {
		context.application = appComponentsLocator.get(context.application.appName);
		context.application.resultsRenderer.setTechieSolutionRenderer(context.application.solutionRenderer);
		context.application.resultsRenderer.setFriendlySolutionRenderer(context.application.friendlySolutionRenderer);
		context.exchangePath = exchangePath;
		context.messagingEnabled = messagingEnabled;
		if(messagingEnabled){
			final Properties properties = new Properties();
			try (final InputStream stream = getClass().getResourceAsStream("/messaging.properties")) {
				properties.load(stream);
			}
			context.messagingProperties = properties;
		}
	}
	

	private static DistributedResultsCollector createResultsCollector(DistributedAlgorithmContext context){
		DistributedResultsCollector collector = context.messagingEnabled ? 
				new KafkaDistributedResultsCollector()
				: new StandardDistributedResultsCollector();
		collector.setup(context);
		return collector;
	}

}
