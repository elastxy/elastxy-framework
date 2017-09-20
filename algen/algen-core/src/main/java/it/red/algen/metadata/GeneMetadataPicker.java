package it.red.algen.metadata;

import it.red.algen.engine.AlgorithmException;
import it.red.algen.utils.Randomizer;

public class GeneMetadataPicker {

	/**
	 * If values not empty, returns a random value, else returns a value between boundaries.
	 * @return
	 */
	public static Object randomPick(GeneMetadata metadata){
		int size = metadata.values.size();
		if(size > 0){
			return metadata.values.get(Randomizer.nextInt(size));
		}
		else if(metadata.min!=null && metadata.max!=null) {
			return randomPickInterval(metadata);
		}
		else {
			throw new AlgorithmException("Cannot generate a random value from metadata values: values is empty or min/max values not set!");
		}
	}
	
	
	
	/**
	 * Returns a random Long value between boundaries
	 * @return
	 */
	private static Object randomPickInterval(GeneMetadata metadata){
		if(metadata.type==GeneMetadataType.INTEGER){
			return (Long)metadata.min + Randomizer.nextLong((Long)metadata.max - (Long)metadata.min + 1);
		}
		else if(metadata.type==GeneMetadataType.DECIMAL){
			return (Double)metadata.min + Randomizer.nextDouble((Double)metadata.max - (Double)metadata.min);
		}
		else {
			throw new AlgorithmException("Cannot pick from an interval if metadata type is not "+GeneMetadataType.INTEGER+" or "+GeneMetadataType.DECIMAL+". Current:"+metadata.type);
		}
	}
	
	

	/**
	 * If values not empty, returns the first in list (always returns the same value).
	 * If values are empty, returns the default value based on type.
	 * @return
	 */
	public static Object pickFirst(GeneMetadata metadata){
		Object result = null;
		int size = metadata.values.size();
		if(size > 0){
			return metadata.values.get(0);
		}
		else {
			switch (metadata.type) {
				case BOOLEAN:	result = false;	break;
				case CHAR: 		result = 'a'; 	break;
				case INTEGER: 	result = 0L; 	break;
				case DECIMAL: 	result = 0.0; 	break;
				case STRING: 	result = "a"; 	break;
				case USER: 
					throw new AlgorithmException("Cannot pick from a User metadata type: first value not defined.");
			}
		}
		return result;
	}
	
}
