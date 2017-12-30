package org.elastxy.web.distributed;

import java.util.Arrays;
import java.util.Base64;

import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.appsupport.ElastXYApplication;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;
import org.elastxy.distributed.tracking.DistributedResultsCollector;
import org.elastxy.web.controller.ExperimentResponse;
import org.elastxy.web.renderer.InternalExperimentResponseRenderer;
import org.elastxy.web.renderer.WebExperimentResponseRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributedApplicationService {
	private static Logger logger = LoggerFactory.getLogger(DistributedApplicationService.class);

	
	@Autowired private AppComponentsLocator appComponentsLocator;
	
	@Autowired private ApplicationsSparkConfig applicationsSparkConfig;
	
	@Autowired private WebExperimentResponseRenderer webRenderer;
	@Autowired private InternalExperimentResponseRenderer intRenderer;
	
	
	
	public ExperimentResponse executeDistributedLocal(DistributedAlgorithmContext context) throws Exception {
		
		// Configure
		setupContext(context);
		SparkTaskConfig taskConfig = applicationsSparkConfig.getTaskConfig(context.application.appName);
		String master = "local[*]";
		byte[] contextBytes = JSONSupport.writeJSONString(context).getBytes(); 
		String contextAsString = Base64.getEncoder().encodeToString(contextBytes);
		
		// Execute job
		String[] params = new String[]{
				context.application.appName, 
				taskConfig.taskIdentifier, 
				taskConfig.sparkHome, 
				taskConfig.outputPath, 
				master, 
				contextAsString};
    	logger.info("Submitting job locally with params: "+Arrays.asList(params));
		ElastXYApplication.main(params);

		// Export results
		MultiColonyExperimentStats stats = DistributedResultsCollector.retrieveResults(taskConfig.outputPath, taskConfig.taskIdentifier);
    	ExperimentResponse response = res(context.requestContext.webRequest, context, stats);
    	
    	return response;
	}
	
	
	
	public ExperimentResponse executeDistributedCluster(DistributedAlgorithmContext context) throws Exception {

		// Configure
		setupContext(context);
    	SparkTaskExecutor executor = new SparkTaskExecutor();
    	SparkTaskConfig taskConfig = applicationsSparkConfig.getTaskConfig(context.application.appName);
    	
    	// Execute job
    	String driverStatus = executor.runDistributed(taskConfig, context);

    	// Export results
		MultiColonyExperimentStats stats = DistributedResultsCollector.retrieveResults(taskConfig.outputPath, taskConfig.taskIdentifier);
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
	
	private void setupContext(AlgorithmContext context) {
		context.application = appComponentsLocator.get(context.application.appName);
		context.application.resultsRenderer.setSolutionRenderer(context.application.solutionRenderer);
	}

}
