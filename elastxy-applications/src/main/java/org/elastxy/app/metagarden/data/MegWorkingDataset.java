package org.elastxy.app.metagarden.data;

import org.elastxy.core.dataprovider.WorkingDataset;

public class MegWorkingDataset implements WorkingDataset {
	public Place[] places;
	public Tree[] trees;
	
	public int getPlacesNumber(){
		return places.length;
	}
}
