package org.elastxy.app.algofrigerator;

import java.util.ArrayList;
import java.util.List;

import org.elastxy.app.algofrigerator.data.IngredientsCoverage;
import org.elastxy.app.algofrigerator.data.Recipe;

public class MefUtils {


	/**
	 * Returns true if at least a partial coverage is given (50% or more present in available foods list),
	 * and calculates list of ingredients available, not available, and coverage at the same time.
	 * 
	 * Fridge foods are weighted double than pantry
	 * 
	 * NOTE: very costly!!! While checking, adds to the raw list of ingredients those 
	 * present in the list of available foods for future usages (check within algorithm).
	 * 
	 * @param availableFoods
	 * @return
	 */
	public static boolean feasibleWith(Recipe recipe, List<String> fridgeFoods, List<String> pantryFoods){
		boolean result = false;
		recipe.ackFridgeIngredients = new ArrayList<String>();
		
		// For each ingredient in recipe, check whether is amongst the available
		// based on similarity: if YES, add the food string value to the raw values
		for(int i=0; i < recipe.ingredients.size(); i++){
			String recipeIngredient = recipe.ingredients.get(i);
			
			// Check if ingredient is acknowledge amongst available
			// from fridge...
			String acknowledgedFood = containsSimilar(fridgeFoods, recipeIngredient);
			if(acknowledgedFood!=null){
				recipe.ackFridgeIngredients.add(acknowledgedFood);
			}
			// or from pantry...
			else {
				acknowledgedFood = containsSimilar(pantryFoods, recipeIngredient);
			}
			if(acknowledgedFood != null){
				recipe.available.add(recipeIngredient);
				
				// Check if it is a main ingredient
				if(recipe.mainIngredient!=null && similar(acknowledgedFood, recipe.mainIngredient)){
					recipe.acknowledgedMainIngredient = acknowledgedFood;
				}

				
			}
			else {
				recipe.notAvailable.add(recipeIngredient);
			}
		}

		
		// Calculate coverage for this recipe
		result = checkCoverage(recipe)!=IngredientsCoverage.NONE;
		
		return result;
	}
	
	/**
	 * Returns first similar String to a given one
	 * @param strings
	 * @param s
	 * @return
	 */
	public static String containsSimilar(List<String> strings, String s){
		String result = null;
		for(int i=0; i < strings.size(); i++){
			String string = strings.get(i);
			if(similar(string,s)){
				result = string;
				break;
			}
		}
		return result;
	}

//	/**
//	 * Returns all similar positions
//	 * @param strings
//	 * @param s
//	 * @return
//	 */
//	private static List<Integer> collectSimilar(List<String> strings, String s){
//		List<Integer> result = new ArrayList<Integer>();
//		for(int i=0; i < strings.size(); i++){
//			if(similar(strings.get(i),s)){
//				result.add(i);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * @param from
//	 * @param minus
//	 */
//	public static void subtractSimilar(List<String> from, List<String> minus){
//		for(int i=0; i < minus.size(); i++){
//			List<Integer> posToRemove = new ArrayList<Integer>();
//			if(!(posToRemove=collectSimilar(from, minus.get(i))).isEmpty()){
//				for(int p=posToRemove.size()-1;p>=0;p--){
//					from.remove((int)posToRemove.get(p));
//				}
//			}
//		}
//	}
	
	
	// TODO3-4: mef: affinity (Levenshtein distance) or semantic affinity, configurable (disabled for performance)
	// TODO3-4: mef: configurable regex for similar ingredients
	public static boolean similar(String singleWord, String phrase){
		return phrase.toLowerCase().matches(".*\\b"+singleWord.toLowerCase()+"\\b.*");
	}
	
	
	/**
	 * Check if recipe is covered fully, partially, none.
	 * Partial: at least 50% of ingredients must be present
	 * 
	 * NOTE: for efficiency, ingredients are not checked by similarity,
	 * but equality only to the list of acknowledge ingredients during
	 * genoma pre-process phase.
	 * 
	 * TODO3-2: mef: check separate pantry available + refrigerator available foods
	 */
	public static IngredientsCoverage checkCoverage(Recipe recipe){
		if(recipe.notAvailable.isEmpty()){
			recipe.coverage = recipe.acknowledgedMainIngredient!=null ? IngredientsCoverage.FULL_MAIN_INGR : IngredientsCoverage.FULL;
		}
		else if(recipe.notAvailable.size() >= Math.ceil((double)recipe.ingredients.size() / 2.0)){
			recipe.coverage = recipe.acknowledgedMainIngredient!=null ? IngredientsCoverage.ONLY_MAIN_INGR : IngredientsCoverage.NONE;
		}
		else {
			recipe.coverage = recipe.acknowledgedMainIngredient!=null ? IngredientsCoverage.PARTIAL_MAIN_INGR : IngredientsCoverage.PARTIAL;
		}
		return recipe.coverage;
	}
	
}
