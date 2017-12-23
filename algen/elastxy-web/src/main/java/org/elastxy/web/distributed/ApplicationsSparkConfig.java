package org.elastxy.web.distributed;

import org.elastxy.core.engine.core.Randomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/**
 * @author red
 *
 */
@Configuration
@PropertySource("classpath:distributed.properties")
public class ApplicationsSparkConfig {
	

	@Value("${spark.home}")
	private String sparkHome;
	
	@Value("${master.uri}")
	private String masterUri;

	@Value("${master.host}")
	private String masterHost;

	@Value("${spark.version}")
	private String sparkVersion;

	@Value("${app.jar.path}")
	private String appJarPath;

	@Value("${main.class}")
	private String mainClass;
	
	@Value("${other.jars.path}")
	private String otherJarsPath;

	/**
	 * Directory path local to driver where to store results.
	 * 
	 * Results are stored in specific files related to executions:
	 * <timestamp>_stats.json, for example.
	 * 
	 */
	@Value("${output.path}")
	private String outputPath;

	
	public SparkTaskConfig getTaskConfig(String application) {
		SparkTaskConfig config = new SparkTaskConfig();
		config.masterURI = masterUri;
		config.masterHost = masterHost;
		config.sparkVersion = sparkVersion;    	
		config.sparkHome = sparkHome;
		config.appName = application;
		config.appJarPath = appJarPath;
		config.mainClass = mainClass;
		config.otherJarsPath = otherJarsPath;
	    config.outputPath = outputPath;
	    config.taskIdentifier = String.format("%d_%d_%s", System.currentTimeMillis(), Randomizer.nextInt(1000), application);
		return config;
	}
	
	public static void main(String[] args){
		String s = String.format("%d_%d_%s", System.currentTimeMillis(), Randomizer.nextInt(1000), "banana");
		System.out.println(s);
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
