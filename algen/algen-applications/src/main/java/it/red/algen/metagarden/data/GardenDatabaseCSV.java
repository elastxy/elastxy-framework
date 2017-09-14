package it.red.algen.metagarden.data;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


/**
 * Reads sample data from resources classpath
 * @author red
 *
 */
public class GardenDatabaseCSV implements GardenDatabase{
	private static final String PLACES_FILENAME = 	"places.csv";
	private static final String TREES_FILENAME = 	"trees.csv";
	
	private String name;
	
	
	public GardenDatabaseCSV(String name){
		this.name = name;
	}
	
	
	public Tree[] getAllTrees() {
		try {
			List<Tree> result = new ArrayList<Tree>();
			String resourceName = "/"+name+"/"+TREES_FILENAME;
			CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(resourceName)), ';');
			String [] nextLine;
			// header
			reader.readNext();
			while ((nextLine = reader.readNext()) != null) {
				result.add(new Tree(
						nextLine[0],
						toInt(nextLine[1]),
						toInt(nextLine[2]),
						toInt(nextLine[3])));
			}
			reader.close();
			return (Tree[])result.toArray(new Tree[result.size()]);
		}
		catch(Throwable t){
			throw new RuntimeException("Error reading trees from db. Ex: "+t, t);
		}
	}
	
	
	public Place[] getAllPlaces() {
		try {
			List<Place> result = new ArrayList<Place>();
			String resourceName = "/"+name+"/"+PLACES_FILENAME;
			CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(resourceName)), ';');
			String [] nextLine;
			// header
			reader.readNext();
			while ((nextLine = reader.readNext()) != null) {
				result.add(new Place(
						toInt(nextLine[0]),
						toInt(nextLine[1]),
						toInt(nextLine[2]),
						nextLine[3]));
			}
			reader.close();
			return (Place[])result.toArray(new Place[result.size()]);
		}
		catch(Throwable t){
			throw new RuntimeException("Error reading places from db: "+t, t);
		}
	}
	
	
	private int toInt(String s){
		return Integer.parseInt(s);
	}
	
	
}
