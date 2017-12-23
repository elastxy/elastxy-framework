package org.elastxy.web.controller;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.elastxy.core.applications.ApplicationException;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.dataprovider.DataAccessException;
import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.elastxy.web.application.InfoService;
import org.elastxy.web.distributed.SparkHealthCheckTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
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
		logger.info("RESPONSE Service HEAD /access => NO_ERROR");
		return "NO_ERROR";
	}
	
	
	/**
	 * TODOM-2: show registered applications
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
    

	/**
	 * Loopback error test controller.
	 * @param code
	 * @return
	 */
    @RequestMapping(path = "/error/{code}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> healthCheck(
			@PathVariable String code) throws IllegalSolutionException {
		logger.info("REQUEST Service GET /error => "+code);
		
		if("1".equals(code)) throw new IllegalArgumentException("handleInput");
		else if("2".equals(code)) throw new DataAccessException("handleInput");
		
		else if("3".equals(code)) throw new ConfigurationException("handleConfiguration");
		else if("4".equals(code)) throw new ApplicationException("handleConfiguration");
		
		else if("5".equals(code)) throw new UnsupportedOperationException("handleNotImplemented");
		
		else if("6".equals(code)) throw new IllegalSolutionException("handleAlgorithm", "legal check");
		else if("7".equals(code)) throw new AlgorithmException("handleAlgorithm");
		else if("8".equals(code)) throw new IllegalStateException("handleAlgorithm");
		
		else if("9".equals(code)) throw new RuntimeException("handleGeneric");
		
		if(true) throw new Error("Wrong parameter code: "+code);
		
    	logger.info("RESPONSE Service GET /error/{code} => Error?");
        return new ResponseEntity<>("ERROR "+code+" NOT THROWN!", HttpStatus.OK);
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
