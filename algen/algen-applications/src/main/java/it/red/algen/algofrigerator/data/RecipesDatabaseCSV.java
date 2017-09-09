package it.red.algen.algofrigerator.data;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;
import it.red.algen.algofrigerator.MefApplication;


// TODOA: cache
public class RecipesDatabaseCSV implements RecipesDatabase {
	private static final String DB_FILENAME = 	"ingredients.csv";

	private String database;
	
	private List<Recipe> recipeCache = new ArrayList<Recipe>();
	
	public RecipesDatabaseCSV(String database){
		this.database = database;
	}
	
	@Override
	public List<Recipe> getAllRecipes() {
		if(!recipeCache.isEmpty()){
			return recipeCache;
		}
		try {
			List<Recipe> result = new ArrayList<Recipe>();
			String resourceName = "/"+MefApplication.APP_NAME+"/"+database+"/"+DB_FILENAME;
			CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(resourceName)), ',');
			String [] nextLine;
			// header
			reader.readNext();
			while ((nextLine = reader.readNext()) != null) {
				Recipe recipe = new Recipe();
				recipe.id = toLong(nextLine[0]);
				recipe.recipeType = RecipeType.fromCode(nextLine[1]);
				recipe.name = nextLine[2];
				recipe.ingredients = Arrays.asList(nextLine[3].split("(\\|)"));
				result.add(recipe);
			}
			reader.close();
			
			// Reduce to those with small number of ingredients (TODOA: configurable)
//			recipeCache = result.stream().filter(p -> p.ingredients.size()<6).collect(Collectors.toList());
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
