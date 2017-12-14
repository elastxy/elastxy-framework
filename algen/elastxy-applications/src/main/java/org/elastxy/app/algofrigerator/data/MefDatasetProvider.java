package org.elastxy.app.algofrigerator.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.elastxy.app.algofrigerator.MefConstants;
import org.elastxy.app.algofrigerator.MefGoal;
import org.elastxy.app.algofrigerator.MefUtils;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.DatasetProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;

public class MefDatasetProvider implements DatasetProvider {
	private static Logger logger = Logger.getLogger(MefDatasetProvider.class);

	private AlgorithmContext context;

	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	
	/**
	 *  Original raw data
	 */
	private RecipesDatabase db = null;

	/**
	 * Extracted Recipe by type
	 */
	private Map<RecipeType, List<Recipe>> recipes = new HashMap<RecipeType, List<Recipe>>();
	
	
	private MefWorkingDataset workingDataset;
	
	@Override
	public WorkingDataset getWorkingDataset(){
		return workingDataset;
	}
	
	/**
	 * Dataset initially collects the list of recipes database, classified by recipe type
	 */
	@Override
	public void collect() {

		String database = context.applicationSpecifics.getParamString(MefConstants.PARAM_DATABASE, MefConstants.DEFAULT_DATABASE);

		// Recipes are read-only. Reloaded if user changes database.
		if(!recipes.isEmpty() && db.getLanguage().equals(database)){
			logger.debug("Found "+recipes.size()+" recipes in cache: no further reading is needed.");
			return;
		}
		
		recipes = new HashMap<RecipeType, List<Recipe>>();
		
		// Load recipes from file
		db = new RecipesDatabaseCSV(context.application.appFolder, database);
		List<Recipe> recipesFromFile = db.getAllRecipes();
		logger.debug("Found "+recipesFromFile.size()+" from file.");
		
		// Classify recipes by type
		recipes.put(RecipeType.SAVOURY, new ArrayList<Recipe>());
		recipes.put(RecipeType.SWEET, 	new ArrayList<Recipe>());
		recipes.put(RecipeType.NEUTRAL, new ArrayList<Recipe>());
		for(int r = 0; r < recipesFromFile.size(); r++){
			Recipe recipe = recipesFromFile.get(r);
			recipes.get(recipe.recipeType).add(recipe);
		}

		for(RecipeType type : RecipeType.values()){
			logger.debug("Classified "+recipes.get(type).size()+" "+type+" recipes.");
		}
	}
	

	@Override
	public void shrink(Target<?, ?> target){

		// Goal
		MefGoal goal = (MefGoal)target.getGoal();
		List<String> fridgeFoods = goal.refrigeratorFoods;
		logger.debug("Requested "+goal.refrigeratorFoods.size()+" foods from refrigerator and "+goal.pantry+" foods from pantry.");
		
		// Restricts to feasible recipes and collecting detailed info on coverage
		logger.debug("Restricting recipes to those feasible with given ingredients.");
		workingDataset = new MefWorkingDataset();
		restrictToFeasible(workingDataset, fridgeFoods, goal.pantry);
		for(RecipeType type : RecipeType.values()){
			logger.debug(type+" type restricted to "+workingDataset.feasibleByType.get(type).size()+" recipes.");
		}
	}
	

	/**
	 * Restricts to feasible recipe and clone every recipe for following computations,
	 * and indicize recipes by id in the meanwhile.
	 * 
	 * @param availableFoods
	 * @return
	 */
	private void restrictToFeasible(MefWorkingDataset dataset, List<String> fridgeFoods, List<String> pantryFoods) {
		Map<Long, Recipe> recipeById = new TreeMap<Long, Recipe>();
		Map<RecipeType, List<Recipe>> feasibleByType = new HashMap<RecipeType, List<Recipe>>();
		feasibleByType.put(RecipeType.SAVOURY, 	new ArrayList<Recipe>());
		feasibleByType.put(RecipeType.SWEET, 	new ArrayList<Recipe>());
		feasibleByType.put(RecipeType.NEUTRAL, 	new ArrayList<Recipe>());
		Iterator<RecipeType> it = this.recipes.keySet().iterator();
		while(it.hasNext()){
			RecipeType rType = it.next();
			List<Recipe> recipesByType = recipes.get(rType);
			for(int r = 0; r < recipesByType.size(); r++){
				Recipe recipe = recipesByType.get(r); // TODOM-1: mef: avoid cocktails
				Recipe copy = recipe.copy();
				if(MefUtils.feasibleWith(copy, fridgeFoods, pantryFoods)){
					feasibleByType.get(rType).add(copy);
					recipeById.put(copy.id, copy);
				}
			}
		}
		dataset.recipeById = recipeById;
		dataset.feasibleByType = feasibleByType;
	}
	
	
}
