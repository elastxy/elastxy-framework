package it.red.algen.algofrigerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.algofrigerator.data.IngredientsCoverage;
import it.red.algen.algofrigerator.data.MefWorkingDataset;
import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.algofrigerator.data.RecipeType;
import it.red.algen.algofrigerator.data.RecipesDatabase;
import it.red.algen.algofrigerator.data.RecipesDatabaseCSV;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.DataAccessException;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.GeneMetadata;
import it.red.algen.metadata.Genes;
import it.red.algen.metadata.StandardMetadataGenoma;


/**
 * 
 *  The genoma is made up of all receipts included in feasible recipes,
 *  which can be associated to foods from refrigerator.
 *  Allele represents one recipe id from the recipes database.
 *  
 *  First, all recipes are filtered excluding those with no ingredient present
 *  in the union between:
 *  - available foods from fridge
 *  - available default foods from pantry
 *  
 *  When creating a new genotype, genes are created linked back to original recipe id,
 *  and put into one of the three chromosomes depending from recipe type.
 *  
 *  The length of each chromosome may vary depending user needs.
 *  
 * TODOM: cache!
 * @author red
 *
 */
@Component
public class MefGenomaProvider implements GenomaProvider {
	private static Logger logger = Logger.getLogger(MefGenomaProvider.class);
	
	public static final String GENE_RECIPE = "_recipe";
	
	@Autowired private MefAlleleGenerator alleleGenerator;

	@Autowired private ContextSupplier contextSupplier;
	
	/**
	 *  Original raw data
	 */
	private RecipesDatabase db = null;

	
	/**
	 * Extracted Recipe by type
	 */
	private Map<RecipeType, List<Recipe>> recipes = new HashMap<RecipeType, List<Recipe>>();
	
	
	/**
	 * Reduced execution-scope data
	 */
	private MefWorkingDataset workingDataset = null;

	
	
	
	
	/**
	 * Genoma is intially void: only when target is set can be set up by reduce()
	 */
	@Override
	public Genoma getGenoma(){
		return null;
//		throw new UnsupportedOperationException("Cannot get a new Genoma: it's based on runtime target restrictions. Use reduce() and maintain the reference for all execution instead.");
	}

	
	/**
	 * Genoma initially collects the list of recipes database, classified by recipe type
	 */
	@Override
	public void collect() {

		// Recipes are read-only
		if(!recipes.isEmpty()){
			logger.debug("Found "+recipes.size()+" recipes in cache: no further reading is needed.");
			return;
		}
		
		recipes = new HashMap<RecipeType, List<Recipe>>();
		
		// Load recipes from file
		String database = contextSupplier.getContext().applicationSpecifics.getParamString(MefApplication.PARAM_DATABASE, MefApplication.DEFAULT_DATABASE);
		db = new RecipesDatabaseCSV(database);
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

	
	
	/**
	 * Create a new restricted Genoma for single execution context,
	 * with only feasible recipes and genes positions based on desired meals number.
	 */
	@Override
	public Genoma reduce(Target<?, ?> target) {
		logger.debug("Reducing the number of recipes and collecting info.");
		
		// Goal
		MefGoal goal = (MefGoal)target.getGoal();
		List<String> fridgeFoods = goal.refrigeratorFoods;
		logger.debug("Requested "+goal.refrigeratorFoods.size()+" foods from refrigerator and "+goal.pantry+" foods from pantry.");
		
		// Restricts to feasible recipes and collecting detailed info on coverage
		logger.debug("Restricting recipes to those feasible with given ingredients.");
		MefWorkingDataset dataset = new MefWorkingDataset();
		restrictToFeasible(dataset, fridgeFoods, goal.pantry);
		for(RecipeType type : RecipeType.values()){
			logger.debug(type+" type restricted to "+dataset.feasibleByType.get(type).size()+" recipes.");
		}

		// Add values to metadata based on target
		// TODOA: check what happens in large set of data if values are with metadata!!! 
		// maybe it's better to access directly through allelegenerator
		Map<RecipeType, Integer> targetRecipesByType = calculateRecipesByType(target);
		
		// Populate metadata genoma
		StandardMetadataGenoma genoma = createGenoma(dataset, targetRecipesByType);
		
		return genoma;
	}





//	/**
//	 * Reset any previous execution information from all recipes
//	 * @return
//	 */
//	private void resetExecutionInfo() {
//		Iterator<RecipeType> it = recipes.keySet().iterator();
//		while(it.hasNext()){
//			RecipeType rType = it.next();
//			List<Recipe> recipesByType = recipes.get(rType);
//			for(int r = 0; r < recipesByType.size(); r++){
//				Recipe recipe = recipesByType.get(r);
//				recipe.acknowledgedIngredients = new ArrayList<String>();
//				recipe.coverage = IngredientsCoverage.UNDEFINED;
//				recipe.available = new ArrayList<String>();
//				recipe.notAvailable = new ArrayList<String>();
//			}
//		}
//	}


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
				Recipe recipe = recipesByType.get(r); // TODOM: avoid cocktails
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
	

	
	/**
	 * 
	 * @param target
	 * @return
	 */
	private Map<RecipeType, Integer> calculateRecipesByType(Target<?, ?> target) {
		MefGoal goal = (MefGoal)target.getGoal(); 
		Map<RecipeType, Integer> recipesByType = new HashMap<RecipeType, Integer>();
		
		// Chromosome length based on desired meals for each type of recipe
		recipesByType.put(RecipeType.SAVOURY, goal.savouryMeals);
		recipesByType.put(RecipeType.SWEET, 	goal.sweetMeals);
		recipesByType.put(RecipeType.NEUTRAL, goal.desiredMeals); // could cover all meals
		return recipesByType;
	}
	
	
	/**
	 * 
	 * @param feasibleByType
	 * @param mealsByType
	 * @return
	 */
	private StandardMetadataGenoma createGenoma(
			MefWorkingDataset workingDataset,
			Map<RecipeType, Integer> mealsByType) {
		Map<String, GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
		Map<String, GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();
		Genes genes = ReadConfigSupport.retrieveGenesMetadata(this.contextSupplier.getContext().application.name);
		Iterator<Map.Entry<RecipeType, List<Recipe>>> it = workingDataset.feasibleByType.entrySet().iterator();
		while(it.hasNext()){
			Entry<RecipeType, List<Recipe>> entryType = it.next();
			String geneCode = entryType.getKey().getCode() + GENE_RECIPE;
			GeneMetadata metadata = genes.metadata.get(geneCode);
			
			// Alleles: recipes by type
			metadata.values = (List<Long>)entryType.getValue().stream().map(r -> r.id).collect(Collectors.toList());
			for(Recipe r : entryType.getValue()){
				if(r.coverage==null || r.coverage==IngredientsCoverage.UNDEFINED){
					throw new DataAccessException("Error while setting allele metadata values: recipe has not been setup. Recipe id: "+r.id);
				}
			}
			
			// Genes by positions
			for(int meal=0; meal < mealsByType.get(entryType.getKey()); meal++){
				genesMetadataByPos.put(genes.positions.get(geneCode).get(0)+meal, metadata); // positions = "x."+"y"
			}
			
			// Genes by code
			genesMetadataByCode.put(metadata.code, metadata);
		}
		
		// Create Genoma
		StandardMetadataGenoma genoma = new StandardMetadataGenoma();
		genoma.setWorkingDataset(workingDataset);
		genoma.setupAlleleGenerator(alleleGenerator);
		genoma.setLimitedAllelesStrategy(false); // TODOM: repetitions of receipt are available: make it configurable!
		genoma.initialize(genesMetadataByCode, genesMetadataByPos);
		return genoma;
	}
	
}
