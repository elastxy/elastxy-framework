package it.red.algen.distributed.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:distributed.properties")
public class DistributedConfig {
	
	@Autowired
	private Environment env;
	
	@Value("${app.name:elastxy}")
	private String appName;
	
	@Value("${spark.home}")
	private String sparkHome;
	
	@Value("${master.uri:local[*]}")
	private String masterUri;
	
	@Bean
	public SparkConf sparkConf() {
	    SparkConf sparkConf = new SparkConf()
	            .setAppName(appName)
	            .setSparkHome(sparkHome)
	            .setMaster(masterUri);
	
	    return sparkConf;
	}
	
	@Bean
	public JavaSparkContext javaSparkContext() {
	    return new JavaSparkContext(sparkConf());
	}
	
	@Bean
	public SparkSession sparkSession() {
	    return SparkSession
	            .builder()
	            .sparkContext(javaSparkContext().sc())
	            .appName("Java Spark SQL basic example")
	            .getOrCreate();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
