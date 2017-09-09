package it.red.algen.algofrigerator.data;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	public Long id;
	public String name;
	public RecipeType recipeType;
	public IngredientsCoverage coverage = IngredientsCoverage.UNDEFINED;

	public List<String> ingredients = new ArrayList<String>();
	public List<String> notAvailable = new ArrayList<String>();
	public List<String> available = new ArrayList<String>();
	
	/**
	 * List of ingredients in a simple string format, from those acknowledge from 
	 * the list of available foods (fridge+pantry) from user (e.g. tomato).
	 * 
	 * This list can be used for efficient comparisons during the algorithm execution.
	 */
	public List<String> acknowledgedIngredients = new ArrayList<String>();

	
	public String toString(){
		return String.format("Recipe:id=%d;name=%s;type=%s;coverage=%s;ingredients=%s;available=%s", id, name, recipeType, coverage, ingredients, available);
	}
}
