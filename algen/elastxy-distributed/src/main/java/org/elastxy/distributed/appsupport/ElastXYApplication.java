package org.elastxy.distributed.appsupport;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.applications.components.factory.AppBootstrapRaw;
import org.elastxy.core.conf.ReadConfigSupport;
import org.elastxy.core.engine.core.Experiment;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.support.JSONSupport;
import org.elastxy.distributed.context.DistributedAlgorithmContext;
import org.elastxy.distributed.engine.core.MultiColonyExperiment;

public class ElastXYApplication {
	private static Logger logger = Logger.getLogger(ElastXYApplication.class);
//	private static org.slf4j.Logger logger2 = org.slf4j.LoggerFactory.getLogger(MexdSparkApplication.class);
	
//	private static transient BufferedWriter stream = null;
	
	
	/**
	 * TODO1-2: make params to Driver optional, use those in local conf if missing (if possible)
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		DistributedAlgorithmContext context = null;
		try {
			
//			stream = new BufferedWriter(new FileWriter("C:\\tmp\\mylog.log"));
			
			// Get application to run
			if(args==null){
				error("No arguments found. Please provide following arguments: "
						+ "applicationName, spark home, master, configuration json.");
				error("Current parameters: "+(args==null?null:Arrays.asList(args)));
				return;
			}
			info("Arguments found: "+Arrays.asList(args));
			String applicationName = args[0]; // expressions_d
			String taskIdentifier = args[1];
			
			info("Initializing application "+applicationName);
			String sparkHome = args[2]; // "C:/dev/spark-2.2.0-bin-hadoop2.7"
			String outputPath = args[3];// "C:/tmp/results
			String master = args[4]; // "spark://192.168.1.101:7077"
			String configBase64 = args[5]; // configuration json input from ws
			String config = new String(Base64.getDecoder().decode(configBase64));
			info("Application config: "+config);
			
			// Register Application
			info("Bootstrapping application.");
			AppBootstrapRaw bootstrap = new AppBootstrapRaw();
			AppComponentsLocator locator = bootstrap.boot(applicationName);
	
			// Create application context
			info("Initializing context.");
			context = (DistributedAlgorithmContext) JSONSupport.readJSONString(config, DistributedAlgorithmContext.class);
			context.application.appName = applicationName;
			setupContext(context, locator);
	
			// Create distributed context
			context.distributedContext = createSparkContext(applicationName, sparkHome, master);
			
			// Execute experiment
			info("Starting application experiment.");
			ExperimentStats stats = executeExperiment(context);
			
			// Store results
			JSONSupport.writeJSONObject(new File(outputPath, taskIdentifier+".json"), stats);
			
			info("Experiment ended: "+stats);
			
		} catch (Exception e) {
			try {
				error("Error while executing SparkApplication. Ex: "+e, e);
			}
			catch(Exception ex2){
				ex2.printStackTrace();
			}
		} finally {
			if(context!=null && context.distributedContext!=null) context.distributedContext.stop();
//			try { if(stream!=null) stream.flush(); } catch(Throwable t){t.printStackTrace();}
		}
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

	private static ExperimentStats executeExperiment(DistributedAlgorithmContext context){
	 	Experiment e = new MultiColonyExperiment(context);
        e.run();
        ExperimentStats stats = e.getStats();
        return stats;
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
	

    private static void info(String message) throws Exception{
    	System.out.println(message);
    	if(logger!=null) logger.info(message);
//    	if(logger2!=null) logger2.info(message);
//    	stream.write(message);
//    	stream.newLine();
    }

    private static void error(String message) throws Exception{
    	System.out.println(message);
    	if(logger!=null) logger.error(message);
//    	if(logger2!=null) logger2.error(message);
//    	stream.write(message);
//    	stream.newLine();
    }

    private static void error(String message, Throwable exception) throws Exception{
    	System.out.println(message);
    	exception.printStackTrace();
    	if(logger!=null) logger.error(message, exception);
//    	if(logger2!=null) logger2.error(message, exception);
//    	stream.write(message+" "+exception.toString());
//    	stream.newLine();
    }
}
