package it.red.algen.algofrigerator.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import it.red.algen.dataprovider.WorkingDataset;

/**
 * Represents the data algorithm is working on, as a filtered copy restriction
 * of the original data, plus execution related information.
 * 
 * E.g. original Recipe is copied into an extended version RecipeDS,
 * hosting all data necessary to execution (coverage, available ingredients).
 * 
 * @author red
 *
 */
public class MefWorkingDataset implements WorkingDataset{
	public Map<RecipeType, List<Recipe>> feasibleByType = new TreeMap<RecipeType, List<Recipe>>();
	public Map<Long, Recipe> recipeById = new HashMap<Long, Recipe>();
	
	public List<Recipe> getRecipes(List<Long> recipesIds){
		List<Recipe> result = recipeById.entrySet().stream()
				.filter(entry -> recipesIds.contains(entry.getKey()))
				.map(entry->entry.getValue())
				.collect(Collectors.toList());
		return result;
	}

}
