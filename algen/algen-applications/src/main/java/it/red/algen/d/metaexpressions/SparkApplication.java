package it.red.algen.d.metaexpressions;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import it.red.algen.applications.components.AppComponentsLocator;
import it.red.algen.applications.components.factory.AppBootstrapRaw;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.engine.core.Experiment;
import it.red.algen.stats.ExperimentStats;

public class SparkApplication {
	private static Logger logger = Logger.getLogger(SparkApplication.class);
	
	public static void main(String[] args){
		try {
			
			// Get application to run
			if(args==null || args.length==0){
				logger.error("No arguments found. Please provide following arguments: "
						+ "applicationName, spark home, master, configuration json.");
				return;
			}
			logger.info("Arguments found: "+Arrays.asList(args));
			String applicationName = args[0]; // expressions.d
			logger.info("Initializing application "+applicationName);
			String sparkHome = args[1]; // "C:/dev/spark-2.2.0-bin-hadoop2.7"
			String master = args[2]; // "spark://192.168.1.101:7077"
			String config = args[3]; // configuration json input from ws
	
			// Register Application
			logger.info("Bootstrapping application.");
			AppBootstrapRaw bootstrap = new AppBootstrapRaw();
			AppComponentsLocator locator = bootstrap.boot(applicationName);
	
			// Create application context
			logger.info("Initializing context.");
			DistributedAlgorithmContext context;
				context = (DistributedAlgorithmContext) ReadConfigSupport.readJSONString(config, DistributedAlgorithmContext.class);
			context.application.name = applicationName;
			setupContext(context, locator);
	
			// Create distributed context
			context.distributedContext = createSparkContext(applicationName, sparkHome, master);
			
			// Execute experiment
			logger.info("Starting application experiment.");
			ExperimentStats stats = executeExperiment(context);
			logger.info("Experiment ended: "+stats);
			
		} catch (IOException e) {
			logger.error("Error while executing SparkApplication. Ex: "+e, e);
		}
	}
	
	private static JavaSparkContext createSparkContext(
			String applicationName,
			String sparkHome,
			String master){
		SparkConf sparkConf = new SparkConf()
	            .setAppName(applicationName)
	            .setSparkHome(sparkHome)
	            .setMaster(master)
	            .set("spark.driver.allowMultipleContexts", "true"); // TODOD: check if ok
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
		return sparkContext;
	}

	private static ExperimentStats executeExperiment(AlgorithmContext context){
	 	Experiment e = new Experiment(context);
        e.run();
        ExperimentStats stats = e.getStats();
        return stats;
	}
	
	private static void setupContext(DistributedAlgorithmContext context, AppComponentsLocator locator) {
		context.application = locator.get(context.application.name);
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context);
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.envFactory.setup(context);
	}
}
