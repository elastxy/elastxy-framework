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
package org.elastxy.web.application;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
//@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
//@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = "org.elastxy")
public class ElastXYWebApplication {
	private static Logger logger = LoggerFactory.getLogger(ElastXYWebApplication.class);

	@Bean(name = "springCM")
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("gene");
	}

	public static void main(String[] args) throws Exception {
		logger.info("ELASTXY ARGUMENTS DUMP: "+(args==null?"No arguments.":Arrays.asList(args)));
		logger.info("ELASTXY SYSTEM PROPERTIES DUMP: "+System.getenv());
		SpringApplication.run(ElastXYWebApplication.class, args);
	}

	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2)  
				.select()                                  
				.apis(RequestHandlerSelectors.any())              
				.paths(PathSelectors.any())                          
				.build()
				.pathMapping("/")
				.apiInfo(metadata());
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("ElastXY REST API documentation")
				.description("see http://elastxy.io")
				.version("0.0.1_ALPHA")
//				.license("Apache 2.0 License")
//				.licenseUrl("https://github.com/ElastXY/master/LICENSE")
				.build();
	}
	
}
