package it.red.algen.algofrigerator.data;

import java.util.List;

public interface RecipesDatabase {
	
	public List<Recipe> getAllRecipes();
	
	public List<Recipe> getRecipes(List<Long> ids);

}
