package it.red.algen.conf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.red.algen.metadata.Genes;

/**
 * Support class for reading configuration or local JSON files
 * @author red
 *
 */
public class ReadConfigSupport {
	private static Logger logger = Logger.getLogger(ReadConfigSupport.class);

	public static Genes retrieveGenesMetadata(String applicationName) {
		Genes genes;
		String classpathResource = "/"+applicationName+"/genes.json";
		try {
			genes = (Genes)ReadConfigSupport.readJSON(classpathResource, Genes.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return genes;
	}
	
	public static Object readJSON(String classpathResource, Class type) throws IOException {
		return readJSON(ReadConfigSupport.class.getResourceAsStream(classpathResource), type);
	}
	
	public static Object readJSON(InputStream inputStream, Class type) throws IOException {
    	ObjectMapper om = new ObjectMapper();
		Object result = om.readValue(inputStream, type);
		return result;
	}

	public static Object readJSONString(String inputString, Class type) throws IOException {
    	ObjectMapper om = new ObjectMapper();
		Object result = om.readValue(inputString, type);
		return result;
	}
	
}
