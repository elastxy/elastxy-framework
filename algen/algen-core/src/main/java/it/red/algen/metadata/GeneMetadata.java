package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneMetadata {
	
	/**
	 * Unique code of the metadata
	 */
	public String code;
	
	/**
	 * Short description of the metadata
	 */
	public String name;
	
	/**
	 * Metadata content type
	 */
	public GeneMetadataType type;
	
	/**
	 * List of admitted possible values, of the specified metadata type
	 */
	public List values = new ArrayList();
	
	/**
	 * The minimum value based on type, if sortable
	 * @return
	 */
	public Object min;
	
	/**
	 * The maximum value based on type, if sortable
	 * @return
	 */
	public Object max;

	
	/**
	 * User defined structure,
	 * preferably a short serializable YAML/JSON representation
	 */
	public Object userStructure;
	
	/**
	 * User defined fixed properties for this metadata gene
	 * 
	 * E.g. location properties for a garden position "sun, wet, wind, ..."
	 * 
	 */
	public Map<String,Object> userProperties = new HashMap<String,Object>();

	
	public String toString(){
		return String.format("GeneMetadata:code=%s,name=%s,type=%s", code, name, type);
	}
	

}
