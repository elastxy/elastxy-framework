package it.red.algen.domain.genetics;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.log4j.Logger;

public class GenomaPositionComparator implements Comparator<String> {
	private static Logger logger = Logger.getLogger(GenomaPositionComparator.class);


	
	@Override
	public int compare(String pos1, String pos2) {
		String[] splitted1 = pos1.split("\\.");
		String[] splitted2 = pos2.split("\\.");
		
		if(splitted1.length!=splitted2.length){
			String msg = String.format("Cannot compare two different positions type: [%s][%s]", splitted1.length, splitted2.length);
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		int result = 0;
		int chunks = splitted1.length;
		int compareChunk1 = new Integer(splitted1[0]).compareTo(new Integer(splitted2[0]));
		
		// Sequence or Strand with different chromosomes
		if(chunks==1 || compareChunk1!=0){
			result = compareChunk1;
		}
		
		else {
			int compareChunk2 = new Integer(splitted1[1]).compareTo(new Integer(splitted2[1]));
			
			// Single strand
			if(chunks==2 || compareChunk2!=0){
				result = compareChunk2;
			}
			
			else {
				int compareChunk3 = new Integer(splitted1[2]).compareTo(new Integer(splitted2[2]));
				result = compareChunk3;
			}
		}
		return result;
	}

}
