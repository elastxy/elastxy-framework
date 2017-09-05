package it.red.algen.algofrigerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.algofrigerator.data.RecipeType;
import it.red.algen.algofrigerator.data.RecipesDatabase;
import it.red.algen.algofrigerator.data.RecipesDatabaseCSV;
import it.red.algen.conf.ConfigurationException;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.ContextSupplier;
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
	

	private RecipesDatabase db = new RecipesDatabaseCSV();
	
	/**
	 * List of available Recipe by type
	 */
	private Map<RecipeType, List<Recipe>> recipes = new HashMap<RecipeType, List<Recipe>>();
	
	
	
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

		// Load recipes from file
		List<Recipe> recipesFromFile = db.getAllRecipes();
		
		// Classify recipes by type
		recipes.put(RecipeType.SAVOURY, new ArrayList<Recipe>());
		recipes.put(RecipeType.SWEET, 	new ArrayList<Recipe>());
		recipes.put(RecipeType.NEUTRAL, new ArrayList<Recipe>());
		for(int r = 0; r < recipesFromFile.size(); r++){
			Recipe recipe = recipesFromFile.get(r);
			recipes.get(recipe.recipeType).add(recipe);
		}
	}

	
	
	/**
	 * Create a new restricted Genoma for single execution context,
	 * with only feasible recipes and genes positions based on desired meals number.
	 */
	@Override
	public Genoma reduce(Target<?, ?> target) {
		
		// Goal
		MefGoal goal = (MefGoal)target.getGoal();
		List<String> availableFoods = goal.refrigeratorFoods;
		availableFoods.addAll(goal.pantry);
		
		// Restricts to feasible recipes
		Map<RecipeType, List<Recipe>> feasibleByType = restrictToFeasible(availableFoods);

		// Add values to metadata based on target
		// TODOA: check what happens in large set of data if values are with metadata!!! 
		// maybe it's better to access directly through allelegenerator
		Map<RecipeType, Integer> mealsByType = calculateMealsByType(target);
		
		// Populate metadata genoma
		StandardMetadataGenoma genoma = createGenoma(feasibleByType, mealsByType);
		
		return genoma;
	}






	/**
	 * 
	 * @param availableFoods
	 * @return
	 */
	private Map<RecipeType, List<Recipe>> restrictToFeasible(List<String> availableFoods) {
		Map<RecipeType, List<Recipe>> feasibleByType = new HashMap<RecipeType, List<Recipe>>();
		feasibleByType.put(RecipeType.SAVOURY, 	new ArrayList<Recipe>());
		feasibleByType.put(RecipeType.SWEET, 	new ArrayList<Recipe>());
		feasibleByType.put(RecipeType.NEUTRAL, 	new ArrayList<Recipe>());
		Iterator<RecipeType> it = this.recipes.keySet().iterator();
		while(it.hasNext()){
			RecipeType rType = it.next();
			List<Recipe> recipesByType = recipes.get(rType);
			for(int r = 0; r < recipesByType.size(); r++){
				Recipe recipe = recipesByType.get(r);
				if(MefUtils.feasibleWith(recipe, availableFoods)){
					feasibleByType.get(rType).add(recipe);
				}
			}
		}
		return feasibleByType;
	}

	
	

	
	/**
	 * 
	 * @param target
	 * @return
	 */
	private Map<RecipeType, Integer> calculateMealsByType(Target<?, ?> target) {
		MefGoal goal = (MefGoal)target.getGoal(); 
		Map<RecipeType, Integer> mealsByType = new HashMap<RecipeType, Integer>();
		
		// Chromosome length based on desired meals for each type of recipe
		mealsByType.put(RecipeType.SAVOURY, goal.savouryMeals);
		mealsByType.put(RecipeType.SWEET, 	goal.sweetMeals);
		mealsByType.put(RecipeType.NEUTRAL, goal.desiredMeals); // could cover all meals
		return mealsByType;
	}
	
	
	/**
	 * 
	 * @param feasibleByType
	 * @param mealsByType
	 * @return
	 */
	private StandardMetadataGenoma createGenoma(Map<RecipeType, List<Recipe>> feasibleByType,
			Map<RecipeType, Integer> mealsByType) {
		Map<String, GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
		Map<String, GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();
		Genes genes = ReadConfigSupport.retrieveGenesMetadata(this.contextSupplier.getContext().application.name);
		Iterator<Map.Entry<RecipeType, List<Recipe>>> it = feasibleByType.entrySet().iterator();
		while(it.hasNext()){
			Entry<RecipeType, List<Recipe>> entryType = it.next();
			String geneCode = entryType.getKey().getCode() + GENE_RECIPE;
			GeneMetadata metadata = genes.metadata.get(geneCode);
			
			// Alleles: recipes by type
			metadata.values = (List<Long>)entryType.getValue().stream().map(r -> r.id).collect(Collectors.toList());
			
			// Genes by positions
			for(int meal=0; meal < mealsByType.get(entryType.getKey()); meal++){
				genesMetadataByPos.put(genes.positions.get(geneCode).get(0)+meal, metadata); // positions = "x."+"y"
			}
			
			// Genes by code
			genesMetadataByCode.put(metadata.code, metadata);
		}
		
		// Create Genoma
		StandardMetadataGenoma genoma = new StandardMetadataGenoma();
		genoma.setupAlleleGenerator(alleleGenerator);
		genoma.setLimitedAllelesStrategy(false); // TODOM: repetitions of receipt are available: make it configurable!
		genoma.initialize(genesMetadataByCode, genesMetadataByPos);
		return genoma;
	}
	
}
