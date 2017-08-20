package it.red.algen.conf;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.red.algen.dataaccess.DataAccessException;

/**
 * Support class for reading configuration or local JSON files
 * @author red
 *
 */
public class ReadConfigSupport {
	private static Logger logger = Logger.getLogger(ReadConfigSupport.class);

	public static Object readJSON(String classpathResource, Class type){
    	ObjectMapper om = new ObjectMapper();
		Object result = null;
		try {
			result = om.readValue(ReadConfigSupport.class.getResourceAsStream(classpathResource), type);
		} catch (IOException e) {
			String msg = "Error while reading JSON resource from classpath:"+classpathResource+" Ex:"+e;
			logger.error(msg);
			throw new DataAccessException(msg, e);
		}
		return result;
	}
	
}
