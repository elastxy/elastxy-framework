package org.elastxy.distributed.appsupport;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.applications.components.factory.AppBootstrapRaw;
import org.elastxy.core.engine.core.Experiment;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.engine.core.MultiColonyExperiment;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;
import org.elastxy.distributed.tracking.DistributedResultsCollector;
import org.elastxy.distributed.tracking.StandardDistributedResultsCollector;
import org.elastxy.distributed.tracking.kafka.KafkaDistributedResultsCollector;

public class ElastXYDriverApplication {
	private static Logger logger = Logger.getLogger(ElastXYDriverApplication.class);
	
	
	/**
	 * TODO1-2: make params to Driver optional, use those in local conf if missing (if possible)
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		DistributedAlgorithmContext context = null;
		DriverApplicationLogging logging = null;
		try {
			logging = new DriverApplicationLogging(logger);
			DriverApplicationParameters parameters = new DriverApplicationParameters(logging);
			
			logger.info("Parsing parameters.");
			if(!parameters.check(args)) return;
			parameters.parse(args);
			
			logger.info("Bootstrapping application.");
			AppBootstrapRaw bootstrap = new AppBootstrapRaw();
			AppComponentsLocator locator = bootstrap.boot(parameters.applicationName);
	
			logging.info("Initializing application context.");
			context = (DistributedAlgorithmContext) JSONSupport.readJSONString(parameters.context, DistributedAlgorithmContext.class);
			context.application.appName = parameters.applicationName;
			setupContext(context, locator);
			
			logging.info("Initializing distributed context.");
			context.exchangePath = parameters.outboundPath;
			context.distributedContext = createSparkContext(parameters.applicationName, parameters.sparkHome, parameters.master);
			context.distributedResultsCollector = createResultsCollector(context);
			
			logging.info("Starting application experiment.");
			MultiColonyExperimentStats stats = executeExperiment(context);

			logging.info("Exporting experiment results.");
			context.distributedResultsCollector.produceResults(parameters.taskIdentifier, stats);
			
			logging.info("Experiment ended: "+stats);
			
		} catch (Exception e) {
			try {
				logging.error("Error while executing SparkApplication. Ex: "+e, e);
			}
			catch(Exception ex2){
				ex2.printStackTrace();
			}
		} finally {
			if(context!=null && context.distributedContext!=null) context.distributedContext.stop();
//			try { if(stream!=null) stream.flush(); } catch(Throwable t){t.printStackTrace();}
		}
	}
	

	
	private static void setupContext(DistributedAlgorithmContext context, AppComponentsLocator locator) {
		context.application = locator.get(context.application.appName);
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context);
		context.application.selector.setup(context);
		context.application.recombinator.setup(context.algorithmParameters);
		context.application.targetBuilder.setup(context);
		context.application.envFactory.setup(context);
//		context.application.resultsRenderer.setup(context);
		
		context.application.multiColonyEnvFactory.setup(context);
		if(context.application.singleColonyDatasetProvider!=null) context.application.singleColonyDatasetProvider.setup(context);
		if(context.application.distributedDatasetProvider!=null) context.application.distributedDatasetProvider.setup(context);
		context.application.distributedGenomaProvider.setup(context);
	}
	
	
	private static JavaSparkContext createSparkContext(
			String applicationName,
			String sparkHome,
			String master){
		SparkConf sparkConf = new SparkConf()
	            .setAppName(applicationName)
	            .setSparkHome(sparkHome)
	            .setMaster(master);
//	            .set("spark.driver.allowMultipleContexts", "true"); // TODO3-1: make it configurable?
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
		return sparkContext;
	}

	
	private static DistributedResultsCollector createResultsCollector(DistributedAlgorithmContext context){
		DistributedResultsCollector collector = context.messagingEnabled ? 
				new KafkaDistributedResultsCollector()
				: new StandardDistributedResultsCollector();
		collector.setup(context);
		return collector;
	}
	
	private static MultiColonyExperimentStats executeExperiment(DistributedAlgorithmContext context){
	 	Experiment e = new MultiColonyExperiment(context);
        e.run();
        ExperimentStats stats = e.getStats();
        return (MultiColonyExperimentStats)stats;
	}
	
	
}
