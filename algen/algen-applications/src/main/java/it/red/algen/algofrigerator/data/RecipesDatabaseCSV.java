package it.red.algen.algofrigerator.data;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;
import it.red.algen.algofrigerator.MefApplication;


// TODOA: cache
public class RecipesDatabaseCSV implements RecipesDatabase {
	private static final String DB_FILENAME = 	"ingredients.csv";

	@Override
	public List<Recipe> getAllRecipes() {
		try {
			List<Recipe> result = new ArrayList<Recipe>();
			String resourceName = "/"+MefApplication.APP_NAME+"/"+DB_FILENAME;
			CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(resourceName)), ',');
			String [] nextLine;
			// header
			reader.readNext();
			long lastId = -1;
			Recipe lastRecipe = null;
			while ((nextLine = reader.readNext()) != null) {
				long currentId = toLong(nextLine[0]);

				// nuova ricetta
				if(currentId!=lastId){
					lastRecipe = new Recipe();
					lastRecipe.id = currentId;
					lastRecipe.recipeType = RecipeType.fromCode(nextLine[1]);
					lastRecipe.name = nextLine[2];
					result.add(lastRecipe);
				}
				
				// nuovo ingrediente (in ogni caso)
				lastRecipe.ingredients.add(nextLine[3]);
				
				lastId = currentId;
			}
			reader.close();
			return result;
		}
		catch(Throwable t){
			throw new RuntimeException("Error reading trees from db. Ex: "+t, t);
		}
	}

	@Override
	public List<Recipe> getRecipes(List<Long> ids) {
		return getAllRecipes().stream().filter(r -> ids.contains(r.id)).collect(Collectors.toList());
	}


	
	private long toLong(String s){
		return Long.parseLong(s);
	}

}
