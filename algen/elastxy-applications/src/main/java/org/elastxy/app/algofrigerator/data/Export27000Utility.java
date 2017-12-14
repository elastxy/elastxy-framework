package org.elastxy.app.algofrigerator.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Export27000Utility {
	private static Logger logger = Logger.getLogger(Export27000Utility.class);

	private static final String INPUT_FILENAME = 	"C:\\tmp\\elastxydata\\algorifero\\ricette_27000.csv";
	private static final String OUTPUT_FILENAME = 	"C:\\tmp\\elastxydata\\algorifero\\ingredients.csv";

	public static void _main(String[] args) throws Exception {
		String nextLine = "1000 G ==== Carne D'agnello|1/2 Cucchiaino ==== Menta Essiccata|1 Foglia ==== Alloro|5 ==== Semi Di Coriandolo|1/2 Bicchiere ==== Olio D'oliva|1 Cucchiaino ==== Zucchero|2 ==== Limoni|1 Pezzetto ==== Cannella|3 ==== Chiodi Di Garofano|50 G ==== Sale Grosso| ==== Pepe In Grani";
		String[] ingr = nextLine.split("\\|");
		System.out.println(Arrays.asList(ingr));
	
		String r = "Vodka alla pesca, acqua";
		boolean contains = r.matches(".*\\bpesca\\b.*");
		System.out.println(r +" => "+contains);
		r = "rana pescatrice";
		contains = r.matches(".*\\bpesca\\b.*");
		System.out.println(r +" => "+contains);
	}
	
	public static void main(String[] args) throws Exception {
		
		// Read and write
		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(new File(INPUT_FILENAME))), ',');
		CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(new File(OUTPUT_FILENAME))));

		// Headers
		//Nome	Tipo_Piatto	Ingr principale	Persone	Note	Ingredienti	Preparazione
		reader.readNext();
		writer.writeNext("RECIPE_ID","RECIPE_TYPE","RECIPE_NAME","INGREDIENTS","MAIN_INGREDIENT","PREPARATION","PERSONS","NOTE");

		// Output variables
		String[] nextLine = null;
		while ((nextLine = reader.readNext()) != null) {
			
			// Id
			int id = Integer.parseInt(nextLine[0]);
			
			// Type
			String type = nextLine[2].trim().equals("Dessert") ? 
					RecipeType.SWEET.getCode() :
					RecipeType.SAVOURY.getCode(); 
			if(nextLine[6].toLowerCase().indexOf("sale")==-1
					&& nextLine[6].toLowerCase().indexOf("zucchero")==-1){ // TODOA-4: mef: segnare ricette neutre
				type = RecipeType.NEUTRAL.getCode();
			}
			
//			// Ingredients
//			String[] inputIngr = nextLine[6].trim().split("(\\|)");
//			List<String> ingredients = Arrays.asList(inputIngr);

			// Persons
			Integer persons = null;
			try {
				persons = nextLine[4]==null ? null : Integer.parseInt(nextLine[4].trim());
			}
			catch(Exception ex){
				logger.error("Error while transforming persons number. Ex:"+ex,ex);
			}
			
			// Write new line
			writer.writeNext(
					String.valueOf(id++), 	// id
					type, 					// type
					nextLine[1].trim(), 	// name
					nextLine[6].trim(),		// ingredients
					nextLine[3].trim(),		// main ingredient
					nextLine[7].trim(),		// preparation
					String.valueOf(persons),// persons
					nextLine[5].trim()		// note
					); 	
		}
		
		reader.close();
		writer.flush();
		writer.close();
	}
}
