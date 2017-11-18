package it.red.algen.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.red.algen.engine.metadata.GeneMetadata;
import it.red.algen.engine.metadata.GeneMetadataType;
import it.red.algen.engine.metadata.GenesMetadataConfiguration;

/**
 * Support class for reading configuration or local JSON files
 * @author red
 *
 */
public class ReadConfigSupport {
	private static Logger logger = Logger.getLogger(ReadConfigSupport.class);

	public static GenesMetadataConfiguration retrieveGenesMetadata(String applicationName) {
		GenesMetadataConfiguration genes;
		String classpathResource = "/"+applicationName+"/genes.json";
		try {
			genes = (GenesMetadataConfiguration)ReadConfigSupport.readJSON(classpathResource, GenesMetadataConfiguration.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		
		// Correct CHARACTER types converting String to Character
		genes.metadata.entrySet().stream().
			filter(e -> e.getValue().type==GeneMetadataType.CHAR).
			forEach(e -> convertStringToChar(e.getValue()));
		
		return genes;
	}
	
	private static void convertStringToChar(GeneMetadata metadata){
		List<Character> charValues = new ArrayList<Character>(metadata.values.size());
		for(Object value  : metadata.values){
			charValues.add(((String)value).charAt(0));
		}
		metadata.values = charValues;
//		metadata.values.stream().forEach(v -> ((String)v).charAt(0));
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


	public static String writeJSONString(Object inputObject) throws IOException {
    	ObjectMapper om = new ObjectMapper();
		String result = om.writeValueAsString(inputObject);
		return result;
	}
}
