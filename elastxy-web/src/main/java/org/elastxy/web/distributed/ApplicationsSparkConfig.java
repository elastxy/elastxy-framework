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
	
	@Value("${spark.master.uri}")
	private String masterUri;

	@Value("${spark.master.host}")
	private String masterHost;

	@Value("${spark.version}")
	private String sparkVersion;

	@Value("${spark.app.jar.path}")
	private String appJarPath;

	@Value("${spark.driver.main.class}")
	private String mainClass;
	
	@Value("${spark.other.jars.path}")
	private String otherJarsPath;

	/**
	 * Directory path local to driver where to store results and output.
	 * 
	 * Results are stored in specific files related to executions:
	 * <timestamp>_stats.json, for example.
	 * 
	 */
	@Value("${driver.outbound.path}")
	private String driverOutboundPath;
	
	/**
	 * Directory local to driver where to retrieve input data.
	 */
	@Value("${driver.inbound.path}")
	private String driverInboundPath;
	
	/**
	 * Directory path local to web application where to retrieve results.
	 * 
	 * Can differ from remoteOutputPath for example when running
	 * in a container, where local path is usually mounted to external.
	 * 
	 */
	@Value("${webapp.inbound.path}")
	private String webappInboundPath;

	
	/**
	 * For monitoring usage.
	 * @param application
	 * @return
	 */
	public SparkTaskConfig getTaskConfig() {
		SparkTaskConfig config = new SparkTaskConfig();
		config.masterURI = masterUri;
		config.masterHost = masterHost;
		config.sparkVersion = sparkVersion;    	
		config.sparkHome = sparkHome;
		return config;
	}
	
	
	/**
	 * For application usage.
	 * @param application
	 * @return
	 */
	public SparkTaskConfig getTaskConfig(String application) {
		SparkTaskConfig config = getTaskConfig();
		config.appName = application;
		config.appJarPath = appJarPath;
		config.mainClass = mainClass;
		config.otherJarsPath = otherJarsPath;
	    config.driverOutboundPath = driverOutboundPath;
	    config.driverInboundPath = driverInboundPath;
	    config.webappInboundPath = webappInboundPath;
	    config.taskIdentifier = String.format("%d_%d_%s", System.currentTimeMillis(), Randomizer.nextInt(1000), application);
		return config;
	}
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
