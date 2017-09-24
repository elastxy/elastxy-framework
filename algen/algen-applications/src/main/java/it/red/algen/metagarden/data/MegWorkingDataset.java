package it.red.algen.metagarden.data;

import it.red.algen.dataaccess.WorkingDataset;

public class MegWorkingDataset implements WorkingDataset {
	public Place[] places;
	public Tree[] trees;
	
	public int getPlacesNumber(){
		return places.length;
	}
}
