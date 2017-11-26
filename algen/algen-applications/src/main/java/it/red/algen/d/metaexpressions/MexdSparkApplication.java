package it.red.algen.d.metaexpressions;

import java.util.Arrays;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import it.red.algen.applications.components.AppComponentsLocator;
import it.red.algen.applications.components.factory.AppBootstrapRaw;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.distributed.context.DistributedAlgorithmContext;
import it.red.algen.distributed.engine.core.MultiColonyExperiment;
import it.red.algen.engine.core.Experiment;
import it.red.algen.stats.ExperimentStats;

public class MexdSparkApplication {
	private static Logger logger = Logger.getLogger(MexdSparkApplication.class);
//	private static org.slf4j.Logger logger2 = org.slf4j.LoggerFactory.getLogger(MexdSparkApplication.class);

	
//	private static transient BufferedWriter stream = null;
	
	/**
	 * TODOD: check if parameters passed to Driver in clustered mode could be avoided (sprk home..)
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
			
			info("Initializing application "+applicationName);
			String sparkHome = args[1]; // "C:/dev/spark-2.2.0-bin-hadoop2.7"
			String master = args[2]; // "spark://192.168.1.101:7077"
			String configBase64 = args[3]; // configuration json input from ws
			String config = new String(Base64.getDecoder().decode(configBase64));
			
			// Register Application
			info("Bootstrapping application.");
			AppBootstrapRaw bootstrap = new AppBootstrapRaw();
			AppComponentsLocator locator = bootstrap.boot(applicationName);
	
			// Create application context
			info("Initializing context.");
			context = (DistributedAlgorithmContext) ReadConfigSupport.readJSONString(config, DistributedAlgorithmContext.class);
			context.application.name = applicationName;
			setupContext(context, locator);
	
			// Create distributed context
			context.distributedContext = createSparkContext(applicationName, sparkHome, master);
			
			// Execute experiment
			info("Starting application experiment.");
			ExperimentStats stats = executeExperiment(context);
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
//	            .set("spark.driver.allowMultipleContexts", "true"); // TODOD: check if ok
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
		context.application = locator.get(context.application.name);
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context);
		context.application.selector.setup(context);
		context.application.envFactory.setup(context);
		context.application.multiColonyEnvFactory.setup(context);
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
