package it.red.algen.metagarden.data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


/**
 * Wellness is expressed from 0 (death of the plant) to 2 (optimal plant growth)
 * @author red
 *
 */
public class GardenWellness {
	public List<Double> locationsUnhappyness = new ArrayList<Double>();
	
	public String toString(){
		OptionalDouble average = locationsUnhappyness.stream().mapToDouble(l -> l).average();
		long total = locationsUnhappyness.stream().filter(u -> u > 1.0).count();
		return String.format("Unhappyness: tot=%d;avg=%.2f", total, average.isPresent()?average.getAsDouble():-1.0);
	}
}
