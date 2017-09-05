package it.red.algen.algofrigerator.data;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	public Long id;
	public String name;
	public RecipeType recipeType;
	public List<String> ingredients = new ArrayList<String>();
	
	public IngredientsCoverage coverage = IngredientsCoverage.UNDEFINED;
	public List<String> notAvailable = new ArrayList<String>();
	
	public String toString(){
		return String.format("Recipe:id=%d;name=%s;type=%s;coverage=%s;ingredients=%s;notAvailable=%s", id, name, recipeType, coverage, ingredients, notAvailable);
	}
}
