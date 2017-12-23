package org.elastxy.web.controller;

import org.apache.log4j.Logger;
import org.elastxy.core.applications.ApplicationException;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.dataprovider.DataAccessException;
import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// TODOM-4: to be completed for every meaningful use case
@ControllerAdvice
public class DistributedControllerExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = Logger.getLogger(DistributedControllerExceptionHandler.class);
 
	/**
	 * ----------------------------------
	 * PRECONDIZIONI
	 * ----------------------------------
	 * Missing or uncorrect configurations, missing or lacking input data...
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	
	// PRECONDITION_FAILED
    @ExceptionHandler(value = { IllegalArgumentException.class, DataAccessException.class })
    protected ResponseEntity<Object> handleInput(RuntimeException ex, WebRequest request) {
    	String bodyOfResponse = "A problem occourred with input parameters or data.";
    	log(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    // PRECONDITION_REQUIRED
    @ExceptionHandler(value = { ConfigurationException.class, ApplicationException.class })
    protected ResponseEntity<Object> handleConfiguration(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "A problem occourred with application configuration.";
    	log(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    
    
	/**
	 * ----------------------------------
	 * DEFECT
	 * ----------------------------------
	 * 
	 * Missing implementations.
	 */
    @ExceptionHandler(value = { UnsupportedOperationException.class })
    protected ResponseEntity<Object> handleNotImplemented(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Feature not yet implemented.";
    	log(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED, request);
    }
    

    
	/**
	 * ----------------------------------
	 * ALGORITHM PROBLEMS
	 * ----------------------------------
	 * 
	 * Unconsistent states or values during algorithm execution.
	 */
    @ExceptionHandler(value = { IllegalSolutionException.class, AlgorithmException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleAlgorithm(Exception ex, WebRequest request) {
        String bodyOfResponse = "A problem occourred within algorithm execution.";
    	log(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    
	/**
	 * ----------------------------------
	 * INTERNAL UNCHECKED BUGS
	 * ----------------------------------
	 */
    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleGeneric(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "A generic unchecked problem occourred internally.";
    	log(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    private void log(String msg, Exception ex){
    	logger.error(msg+""+ex, ex);
    }
}