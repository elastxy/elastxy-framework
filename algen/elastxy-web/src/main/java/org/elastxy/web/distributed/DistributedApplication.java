package org.elastxy.web.distributed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource(value={"classpath:distributed.properties"}, ignoreResourceNotFound = true)
@ComponentScan(basePackages = "org.elastxy.web")
public class DistributedApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedApplication.class, args);
	}
	
}
