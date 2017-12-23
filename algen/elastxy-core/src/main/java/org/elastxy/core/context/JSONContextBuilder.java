package org.elastxy.core.context;

import java.io.IOException;

import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.support.JSONSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Returns the context given a JSON as a input
 * 
 * @author red
 *
 */
@Component
public class JSONContextBuilder implements ContextBuilder {
	private static Logger logger = LoggerFactory.getLogger(JSONContextBuilder.class);

	/**
	 * Retrieve from a classpath resource the json file for
	 * a benchmark or a standard experiment execution from:
	 * classpath:/{applicationName}/{fileName}.json
	 * 
	 * Where file names must be:
	 * - "benchmark" for benchmark
	 * - "experiment" for experiment
	 * 
	 */
	@Override
	public AlgorithmContext build(String applicationName, boolean benchmark) {
		String fileName = benchmark ? "benchmark" : "experiment";
		String classpathResource = "/"+applicationName+"/"+fileName+".json";
		
		AlgorithmContext result;
		try {
			result = (AlgorithmContext)JSONSupport.readJSON(classpathResource, AlgorithmContext.class);
			result.application.appName = applicationName;
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		
		return result;
	}
}
