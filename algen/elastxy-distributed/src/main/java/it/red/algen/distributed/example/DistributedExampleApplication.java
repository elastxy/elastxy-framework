package it.red.algen.distributed.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource(value={"classpath:distributed.properties"}, ignoreResourceNotFound = true)
@ComponentScan(basePackages = "it.red.algen.distributed.example")
public class DistributedExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedExampleApplication.class, args);
	}
	
}
