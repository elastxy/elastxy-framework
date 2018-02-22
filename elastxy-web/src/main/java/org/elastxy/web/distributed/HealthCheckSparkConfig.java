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

import org.apache.spark.SparkConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


/**
 * @author red
 *
 */
@Configuration
@PropertySource("classpath:distributed-healthcheck.properties")
public class HealthCheckSparkConfig {
	
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
