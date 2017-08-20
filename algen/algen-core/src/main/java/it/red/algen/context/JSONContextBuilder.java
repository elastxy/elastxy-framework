package it.red.algen.context;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		AlgorithmContext result = null;
		String fileName = benchmark ? "benchmark" : "experiment";
		ObjectMapper om = new ObjectMapper();
		String classpathResource = "/"+applicationName+"/"+fileName+".json";
		try {
			result = om.readValue(getClass().getResourceAsStream(classpathResource), AlgorithmContext.class);
		} catch (IOException e) {
			String msg = "Error while mapping AlgorithmContext from classpath:"+classpathResource+" Ex:"+e;
			logger.error(msg);
			throw new ContextException(msg, e);
		}
		
		return result;
	}

}
