package it.red.algen.distributed;

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
	    return config;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
