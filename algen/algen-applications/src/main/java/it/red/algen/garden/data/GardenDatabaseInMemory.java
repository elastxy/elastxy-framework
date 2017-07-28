package it.red.algen.garden.data;

import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

public class GardenDatabaseInMemory implements GardenDatabase {
	
	public Tree[] getAllTrees(){
		Tree rose = new Tree("Rosa", 1,1,0);
		Tree gelsomin = new Tree("Gelsomino", 0,1,1);
		Tree geranium = new Tree("Geranium", 2,2,2);
		return new Tree[]{rose,gelsomin,geranium};
	}
	
	public Place[] getAllPlaces(){
		Place sunny = new Place(2,2,2);
		Place wetty = new Place(0,1,1);
		Place windy = new Place(1,1,0);
		return new Place[]{sunny, wetty, windy};
	}

//	public Tree[] getAllTrees(){
//		Tree rose = new Tree("Rosa", 2,1,1);
//		Tree gelsomin = new Tree("Gelsomino", 1,2,2);
//		Tree geranium = new Tree("Geranium", 2,2,2);
//		return new Tree[]{rose,gelsomin,geranium};
//	}
//	
//	public Place[] getAllPlaces(){
//		Place sunny = new Place(2,0,1);
//		Place wetty = new Place(1,2,0);
//		Place windy = new Place(1,1,2);
//		return new Place[]{sunny, wetty, windy};
//	}

}
