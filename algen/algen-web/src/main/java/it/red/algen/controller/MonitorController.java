package it.red.algen.controller;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.red.algen.application.InfoService;
import it.red.algen.distributed.SparkHealthCheckTask;

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
		logger.info("REQUEST Service /access => (empty)");
		logger.info("RESPONSE Service /access => OK");
		return "OK";
	}
	
	
	/**
	 * TODOM: show registered applications
	 * @return
	 */
	@RequestMapping(path = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> hello() {
		logger.info("REQUEST Service /hello => (empty)");
		logger.info("RESPONSE Service /hello => Message");
		return Collections.singletonMap("message", infoService.getInfoMessage());
	}
	

	
    @RequestMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
    	String localResult = runHealthCheck(sparkConfLocal);
    	String remoteResult = runHealthCheck(sparkConfRemote);
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
