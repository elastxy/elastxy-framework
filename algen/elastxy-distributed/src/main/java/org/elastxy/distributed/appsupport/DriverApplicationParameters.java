package org.elastxy.distributed.appsupport;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;

import org.elastxy.core.conf.ConfigurationException;

public class DriverApplicationParameters {
	private DriverApplicationLogging logger;
	
	
	String applicationName;
	String taskIdentifier;
	
	String sparkHome;
	String inboundPath;
	String outboundPath;
	
	String master;
	String context;

	
	public DriverApplicationParameters(DriverApplicationLogging logger){
		this.logger = logger;
	}
	
	
	public boolean check(String[] args) throws Exception {
		logger.info("Check arguments: "+args);
		if(args==null){
			logger.error("No arguments found. Please provide following arguments: "
					+ "applicationName, spark home, master, configuration json.");
			logger.error("Current parameters: "+(args==null?null:Arrays.asList(args)));
			return false;
		}
		return true;
	}
	
	
	public void parse(String[] args) throws Exception {
		logger.info("Arguments found: "+Arrays.asList(args));
		applicationName = args[0]; // expressions_d
		taskIdentifier = args[1];
		
		logger.info("Initializing application "+applicationName);
		sparkHome = args[2]; // e.g. "C:/dev/spark-2.2.0-bin-hadoop2.7"
		checkPath(sparkHome);
		inboundPath = args[3];// e.g. "C:/tmp/inbound
		checkPath(inboundPath);
		outboundPath = args[4];// e.g. "C:/tmp/results
		checkPath(outboundPath);
		
		master = args[5]; // "spark://192.168.1.101:7077"
		String contextBase64 = args[6]; // context configuration json input from ws
		context = new String(Base64.getDecoder().decode(contextBase64));
		logger.info("Application config: "+context);
	}
	
	private static void checkPath(String path){
		if(!new File(path).exists()){
			throw new ConfigurationException("Distributed execution error: following path not found: "+path);
		}
	}
}
