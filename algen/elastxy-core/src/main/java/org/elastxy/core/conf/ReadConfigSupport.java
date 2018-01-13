package org.elastxy.core.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elastxy.core.engine.metadata.GeneMetadata;
import org.elastxy.core.engine.metadata.GeneMetadataType;
import org.elastxy.core.engine.metadata.GenesMetadataConfiguration;
import org.elastxy.core.support.JSONSupport;

/**
 * Support class for reading configuration or local JSON files
 * @author red
 *
 */
public class ReadConfigSupport {
	private static Logger logger = Logger.getLogger(ReadConfigSupport.class);

	public static GenesMetadataConfiguration retrieveGenesMetadata(String applicationName) {
		GenesMetadataConfiguration genes;
		String classpathResource = JSONSupport.checkClasspathResource(applicationName, "genes.json");
		try {
			genes = (GenesMetadataConfiguration)JSONSupport.readJSON(classpathResource, GenesMetadataConfiguration.class);
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
	
}
