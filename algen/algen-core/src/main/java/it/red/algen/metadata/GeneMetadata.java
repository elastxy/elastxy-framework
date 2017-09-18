package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.context.Randomizer;

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
	 * After the Gene is given a new Allele in the Genoma Provider, cannot be modified further
	 */
	public boolean blocked;
	
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

	
	/**
	 * If values not empty, returns a random value, else returns a value between boundaries.
	 * @return
	 */
	public Object randomPick(){
		int size = values.size();
		if(size > 0){
			return values.get(Randomizer.nextInt(size));
		}
		else if(min!=null && max!=null) {
			return randomPickInterval();
		}
		else {
			throw new IllegalArgumentException("Cannot generate a random value from metadata values: values is empty or min/max values not set!");
		}
	}
	
	
	/**
	 * Returns a random Long value between boundaries
	 * @return
	 */
	private Object randomPickInterval(){
		if(type==GeneMetadataType.INTEGER){
			return (Long)min + Randomizer.nextLong((Long)max - (Long)min + 1);
		}
		else if(type==GeneMetadataType.DECIMAL){
			return (Double)min + Randomizer.nextDouble((Double)max - (Double)min);
		}
		else {
			throw new IllegalStateException("Cannot pick from an interval if metadata type is not "+GeneMetadataType.INTEGER+" or "+GeneMetadataType.DECIMAL+". Current:"+type);
		}
	}
	
	

	/**
	 * If values not empty, returns the first in list (always returns the same value).
	 * If values are empty, returns the default value based on type.
	 * @return
	 */
	public Object pickFirst(){
		Object result = null;
		int size = values.size();
		if(size > 0){
			return values.get(0);
		}
		else {
			if(type==GeneMetadataType.BOOLEAN){
				result = false;
			}
			else if(type==GeneMetadataType.CHAR){
				result = 'a';
			}
			else if(type==GeneMetadataType.INTEGER){
				result = 0L;
			}
			else if(type==GeneMetadataType.DECIMAL){
				result = 0.0;
			}
			else if(type==GeneMetadataType.STRING){
				result = "a";
			}
			else if(type==GeneMetadataType.USER){
				throw new IllegalStateException("Cannot pick from a User metadata type: first value not defined.");
			}
		}
		return result;
	}
	
	
	public String toString(){
		return String.format("GeneMetadata:code=%s,name=%s,type=%s", code, name, type);
	}
	

}
