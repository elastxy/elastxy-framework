package it.red.algen.garden.domain;

import it.red.algen.domain.interfaces.RawFitness;

public class GardenRawFitness implements RawFitness {
	public int rawFitness;
	
	public GardenRawFitness(int rawFitness){
		this.rawFitness = rawFitness;
	}
}
