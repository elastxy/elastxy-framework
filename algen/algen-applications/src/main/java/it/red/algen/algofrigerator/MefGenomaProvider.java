package it.red.algen.algofrigerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import it.red.algen.algofrigerator.data.IngredientsCoverage;
import it.red.algen.algofrigerator.data.MefWorkingDataset;
import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.algofrigerator.data.RecipeType;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.DataAccessException;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.WorkingDataset;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.engine.metadata.GeneMetadata;
import it.red.algen.engine.metadata.GenesMetadataConfiguration;
import it.red.algen.engine.metadata.StandardMetadataGenoma;


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
public class MefGenomaProvider implements GenomaProvider {
	private static Logger logger = Logger.getLogger(MefGenomaProvider.class);
	
	public static final String GENE_RECIPE = "_recipe";
	
	private AlgorithmContext context;
	
	
	
	/**
	 * Reduced execution-scope data
	 */
	private MefWorkingDataset workingDataset = null;

	
	public void setup(AlgorithmContext context){
		this.context = context;
	}

	/**
	 * Assign a working data set for accessing data, if needed.
	 * @param workingDataset
	 */
	public void setWorkingDataset(WorkingDataset workingDataset){
		this.workingDataset = (MefWorkingDataset)workingDataset;
	}
	
	
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
	}

	
	
	/**
	 * Create a new restricted Genoma for single execution context,
	 * with only feasible recipes and genes positions based on desired meals number.
	 */
	@Override
	public Genoma shrink(Target<?, ?> target) {
		logger.debug("Reducing the number of recipes and collecting info.");

		// Add values to metadata based on target
		// TODOA: check what happens in large set of data if values are with metadata!!! 
		// maybe it's better to access directly through allelegenerator
		Map<RecipeType, Integer> targetRecipesByType = calculateRecipesByType(target);
		
		// Populate metadata genoma
		StandardMetadataGenoma genoma = createGenoma(targetRecipesByType);
		
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
			Map<RecipeType, Integer> mealsByType) {
		Map<String, GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
		Map<String, GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();
		GenesMetadataConfiguration genes = ReadConfigSupport.retrieveGenesMetadata(context.application.name);
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
		genoma.setupAlleleGenerator(context.application.alleleGenerator);
		genoma.setLimitedAllelesStrategy(false); // TODOM: repetitions of receipt are available: make it configurable!
		genoma.initialize(genesMetadataByCode, genesMetadataByPos);
		return genoma;
	}
	
}
