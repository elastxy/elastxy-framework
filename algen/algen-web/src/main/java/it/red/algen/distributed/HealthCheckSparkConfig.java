package it.red.algen.distributed;

import org.apache.spark.SparkConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


/**
 * TODOA: Reuse for applications config, not only healthcheck
 * @author red
 *
 */
@Configuration
@PropertySource("classpath:distributed-healthcheck.properties")
public class DistributedConfig {
	
	@SuppressWarnings("unused")
	@Autowired
	private Environment env;
	
	@Value("${app.name}")
	private String appName;
	
	@Value("${spark.home}")
	private String sparkHome;

	@Value("${spark.version}")
	private String sparkVersion;
	
	@Value("${master.uri.local}")
	private String masterUriLocal;

	@Value("${master.uri.remote}")
	private String masterUriRemote;
	
	@Value("${jars.path}")
	private String jarsPath;

//	@Value("${spark.log4j.configuration}")
//	private String sparkLog4jConfiguration;
//
//	@Value("${spark.eventLog.enabled}")
//	private String sparkHistoryEventsEnabled;
//
//	@Value("${spark.eventLog.dir}")
//	private String sparkHistoryEventsPath;

//	@Value("${spark.history.fs.logDirectory}")
//	private String sparkHistoryEventsLogdir;
	
	
	
	@Bean(name = "sparkConfLocal")
	public SparkConf sparkConfLocal() {
	    SparkConf sparkConf = new SparkConf()
	            .setAppName(appName)
	            .setSparkHome(sparkHome)
	            .setMaster(masterUriLocal)
	            .setJars(new String[]{jarsPath}); // only web jar
	
	    return sparkConf;
	}
	
	@Bean(name = "sparkConfRemote")
	public SparkConf sparkConfRemote() {
	    SparkConf sparkConf = new SparkConf()
	            .setAppName(appName)
	            .setSparkHome(sparkHome)
	            .setMaster(masterUriRemote)
	            .setJars(new String[]{jarsPath}); // only web jar
	
	    return sparkConf;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
