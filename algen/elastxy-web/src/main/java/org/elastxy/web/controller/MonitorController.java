package org.elastxy.web.controller;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.elastxy.web.application.InfoService;
import org.elastxy.web.distributed.SparkHealthCheckTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODOA-2: filter out request to monitoring services using a Filter
 * TODOA-2: filter out request to Spring Boot monitoring with a Filter
 * @author red
 *
 */
@Controller
@RequestMapping(path = "/monitor")
public class MonitorController {
	private static transient Logger logger = Logger.getLogger(MonitorController.class);

	@Autowired private InfoService infoService;

	@Autowired
	private SparkConf sparkConfLocal;
	
	@Autowired
	private SparkConf sparkConfRemote;

	@Value("${test.file.path}")
	private String testFilePath;
	
	
	
	@RequestMapping(path = "/access", method = RequestMethod.HEAD)
	@ResponseBody
	public String access() {
		logger.info("REQUEST Service HEAD /access => (empty)");
		logger.info("RESPONSE Service HEAD /access => OK");
		return "OK";
	}
	
	
	/**
	 * TODOA-2: show registered applications
	 * TODOM-8: let register/deregister an application
	 * @return
	 */
	@RequestMapping(path = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> hello() {
		logger.info("REQUEST Service GET /info => (empty)");
		logger.info("RESPONSE Service GET /info => Message");
		return Collections.singletonMap("message", infoService.getInfoMessage());
	}
	
	
    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public ResponseEntity<String> healthCheck() {
		logger.info("REQUEST Service GET /healthcheck => (empty)");
    	String localResult = runHealthCheck(sparkConfLocal);
    	String remoteResult = runHealthCheck(sparkConfRemote);
    	logger.info("RESPONSE Service GET /healthcheck => Message");
        return new ResponseEntity<>("LOCAL: \n"+localResult+"\nREMOTE: \n"+remoteResult, HttpStatus.OK);
    }
    
    
	private String runHealthCheck(SparkConf sparkConf) {
		String result;
		JavaSparkContext context = new JavaSparkContext(sparkConf);
		try {
    		result = SparkHealthCheckTask.run(context, testFilePath);
    	}
    	catch(Throwable t){
    		result = "Error calling healthcheck! Ex: "+t;
    		logger.fatal(result, t);
    	}
    	finally {
    		context.stop();
    	}
		return result;
	}
}
