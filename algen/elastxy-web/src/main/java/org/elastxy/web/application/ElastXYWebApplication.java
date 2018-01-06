/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elastxy.web.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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

	@Bean(name = "springCM")
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("gene");
	}

	public static void main(String[] args) throws Exception {
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
				.version("1.0-ALPHA")
//				.license("Apache 2.0 License")
//				.licenseUrl("https://github.com/ElastXY/master/LICENSE")
				.build();
	}
}
