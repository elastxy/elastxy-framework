package it.red.algen.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Genes pojo for reading configuration metadata.
 * @author red
 *
 */
public class GenesMetadataConfiguration {
	public Map<String, GeneMetadata> metadata = new HashMap<String, GeneMetadata>(); 
	public Map<String, List<String>> positions = new HashMap<String, List<String>>(); 
}
