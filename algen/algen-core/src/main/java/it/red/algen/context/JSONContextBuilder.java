package it.red.algen.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ReadConfigSupport;

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
		
		AlgorithmContext result = (AlgorithmContext)ReadConfigSupport.readJSON(classpathResource, AlgorithmContext.class);
		
		return result;
	}
}
