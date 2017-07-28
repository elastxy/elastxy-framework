package it.red.algen.garden.data;

import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

public interface GardenDatabase {
		
	public Tree[] getAllTrees();
	
	public Place[] getAllPlaces();
	
}
