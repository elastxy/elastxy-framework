package org.elastxy.web.controller;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.Locale;
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
import org.elastxy.web.distributed.SparkJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@Autowired private SparkJobService sparkJobService;
	
	@Autowired private SparkConf sparkConfLocal;
	
	@Autowired private SparkConf sparkConfRemote;

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
	 * TODO3-2: show registered applications, when Web-Request not present
	 * TODO2-8: let register/deregister an application
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
    
    

    @RequestMapping(path = "/jobs/{jobId}/status", method = RequestMethod.GET)
	@ResponseBody
    public ExperimentResponse checkJobStatus(
    		@PathVariable String jobId,  
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) throws Exception {
    	logger.info("REQUEST Service /jobs/{jobId}/status => "+jobId);
    	if(webRequest) throw new IllegalAccessError("API not allowed.");
    	
    	String status = sparkJobService.checkJobStatus(jobId);
		ExperimentResponse response = new ExperimentResponse();
		response.status = ResponseStatus.OK;
		response.content = "Job "+jobId+" status:" + status;
		
    	logger.info("RESPONSE Service /jobs/{jobId}/status => "+response.status);
    	return response;
    }
    

    @RequestMapping(path = "/jobs/{jobId}/kill", method = RequestMethod.PUT)
	@ResponseBody
    public ExperimentResponse killJob(
    		@PathVariable String jobId,  
			@RequestHeader(value="Web-Request", defaultValue="true") boolean webRequest,
			Locale userLocale) throws Exception {
    	logger.info("REQUEST Service /jobs/{jobId}/kill => "+jobId);
    	if(webRequest) throw new IllegalAccessError("API not allowed.");
    	
    	boolean status = sparkJobService.killJob(jobId);
		ExperimentResponse response = new ExperimentResponse();
		response.status = ResponseStatus.OK;
		response.content = "Job "+jobId+" killed: "+status;
		
    	logger.info("RESPONSE Service /jobs/{jobId}/kill => "+response.status);
    	return response;
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
		
		else if("10".equals(code)) throw new AccessControlException("handleAuthorization");
		else if("11".equals(code)) throw new SecurityException("handleAuthentication");
		
		if(true) throw new Error("Wrong parameter code: "+code);
		
    	logger.info("RESPONSE Service GET /error/{code} => Error?"); // code not reachable, it's ok ;)
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
