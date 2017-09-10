package it.red.algen.algofrigerator.data;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	public Long id;
	public String name;
	public RecipeType recipeType;
	public List<String> ingredients = new ArrayList<String>();
	public String mainIngredient;
	public String preparation;
	public Integer persons;
	public String note;
	
	public IngredientsCoverage coverage = IngredientsCoverage.UNDEFINED;
	public List<String> notAvailable = new ArrayList<String>();
	public List<String> available = new ArrayList<String>();
	
	/**
	 * List of ingredients in a simple string format, from those acknowledge from 
	 * the list of available foods (fridge+pantry) from user (e.g. tomato).
	 * 
	 * This list can be used for efficient comparisons during the algorithm execution.
	 */
	public List<String> ackFridgeIngredients = new ArrayList<String>();
	public String acknowledgedMainIngredient;
	

	/**
	 * Copy all fixed values, not dependent to execution
	 * @param original
	 * @return
	 */
	public Recipe copy(){
		Recipe copy = new Recipe();
		copy.id = id;
		copy.name = name;
		copy.recipeType = recipeType;
		copy.ingredients = ingredients;
		copy.mainIngredient = mainIngredient;
		copy.preparation = preparation; // TODOA: solo per renderer
		copy.persons = persons;
		copy.note = note;
		return copy;
	}
	
	public String toString(){
		return String.format("Recipe:id=%d;name=%s;type=%s;coverage=%s;mainIngredient=%s;available=%s", id, name, recipeType, coverage, mainIngredient, available);
	}
}
