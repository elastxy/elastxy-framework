package it.red.algen.algofrigerator;

import java.util.ArrayList;
import java.util.List;

import it.red.algen.algofrigerator.data.IngredientsCoverage;
import it.red.algen.algofrigerator.data.Recipe;

public class MefUtils {


	/**
	 * Returns true if at least one ingredient is present in available foods list
	 * @param availableFoods
	 * @return
	 */
	public static boolean feasibleWith(Recipe recipe, List<String> availableFoods){
		boolean result = false;
		// TODOB: with streams
		for(int i=0; i < recipe.ingredients.size(); i++){
			if(availableFoods.contains(recipe.ingredients.get(i))){
				result = true;
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * Check if recipe is covered fully, partially, none.
	 * 
	 * TODOA: pantry+refrigerator foods
	 */
	public static IngredientsCoverage checkCoverage(Recipe recipe, List<String> refrigeratorFoods){
		List<String> notAvailable = new ArrayList<String>(recipe.ingredients);
		notAvailable.removeAll(refrigeratorFoods);
		recipe.notAvailable = notAvailable;

		if(notAvailable.isEmpty()){
			recipe.coverage = IngredientsCoverage.FULL;
		}
		else if(notAvailable.size()==recipe.ingredients.size()){
			recipe.coverage = IngredientsCoverage.NONE;
		}
		else {
			recipe.coverage = IngredientsCoverage.PARTIAL;
		}
		return recipe.coverage;
	}
	
}
