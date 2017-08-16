package it.red.algen.metagarden.data;

public class GardenDatabaseInMemory implements GardenDatabase {
	
	public Tree[] getAllTrees(){
		Tree rose = new Tree("Rosa", 1,1,0);
		Tree gelsomin = new Tree("Gelsomino", 2,2,2);
		Tree geranium = new Tree("Geranium", 2,2,2);
		return new Tree[]{rose,gelsomin,geranium};
	}
	
	public Place[] getAllPlaces(){
		Place sunny = new Place(1,1,0,"davanzale");
		Place wetty = new Place(1,1,0,"muro");
		Place windy = new Place(1,1,0,"ringhiera");
		return new Place[]{sunny, wetty, windy};
	}


//	public Tree[] getAllTrees(){
//		Tree rose = new Tree("Rosa", 1,1,0);
//		Tree gelsomin = new Tree("Gelsomino", 0,1,1);
//		Tree geranium = new Tree("Geranium", 2,2,2);
//		return new Tree[]{rose,gelsomin,geranium};
//	}
//	
//	public Place[] getAllPlaces(){
//		Place sunny = new Place(1,1,0,"davanzale");
//		Place wetty = new Place(1,1,0,"muro");
//		Place windy = new Place(1,1,0,"ringhiera");
//		return new Place[]{sunny, wetty, windy};
//	}

}
