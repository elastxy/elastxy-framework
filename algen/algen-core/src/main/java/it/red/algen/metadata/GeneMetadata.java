package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.engine.AlgorithmException;
import it.red.algen.utils.Randomizer;

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
	 * Metadata content type of Allele
	 */
	public GeneMetadataType type;
	
	/**
	 * List of admitted possible values, of the specified metadata type.
	 * TODOM: If content is big, an implementation of an interface 
	 * GeneMetadataValuesProvider could be provided instead.
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

	
//	/**
//	 * After the Gene is given a new Allele in the Genoma Provider, cannot be modified further
//	 */
//	public boolean blocked;
	
	/**
	 * User defined structure to be used in application specific logics,
	 * preferably a short serializable YAML/JSON representation, or a small binary content.
	 */
	public Object userStructure;
	
	/**
	 * User defined fixed properties for this metadata gene.
	 * 
	 * E.g. location properties for a garden position "sun, wet, wind, ..."
	 * 
	 */
	public Map<String,Object> userProperties = new HashMap<String,Object>();

	
	public String toString(){
		return String.format("GeneMetadata:code=%s,name=%s,type=%s", code, name, type);
	}
	

}
