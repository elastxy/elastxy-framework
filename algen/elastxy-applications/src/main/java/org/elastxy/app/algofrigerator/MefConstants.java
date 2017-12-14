package org.elastxy.app.algofrigerator;

/**
 * 
 * ALGORIFERO
 * 
 * Target
 * 
 * This is a multi-goal problem.
 * Fill the maximum number of recipes with ingredients from refrigerator.
 * Total recipes should be the given number N, if possible, distributed between
 * X% savoury (Na = N * X%), Y% sweet (Nw = N * Y%).
 * User can express the desidered proportion of savoury or sweet recipes (e.g. 70 vs. 30).
 * 
 * Goal is expressed as the list of all food available from refrigerator, plus the number 
 * of desired recipes and percentage composition between savoury and sweet.
 * 
 * Neutral recipes can also be added to both categories to match target number and percentage.
 * 
 *
 * Genoma: one type of gene assigned to all chromosomes, reflecting the recipe id.
 * 		Initial genoma for the single experiment is filtered to exclude 
 * 		recipes with no ingredients present in given goal foods.
 * 		TODOM-4: mef: filter by forbidden ingredients (e.g. gluten, meat...), cuisine type (e.g. french), and so on...
 * 		A list of evergreen pantry default ingredients may be provided to complete recipes (salt, oil, 
 * 		winegar, sugar...)
 * 
 * Solution genotype: a number of three chromosomes, for sweet, savoury or neutral recipes.
 * 		Every gene hosts a recipe, the order is not important.
 * 		The allele is a recipe id randomly taken from all available recipes.
 * 		Dimensions of chromosomes are: Na, Nw, and Nn = Na + Nw (for covering all lacking recipes 
 * 		in the worst case.
 * 		
 * Solution phenotype: the list of sweet, savoury or neutral recipes completed or partially 
 * 		completed with the ingredients from refrigerator.
 * 		Phenotype is built filling all recipes with available ingredients from fridge,
 * 		starting from savoury, then sweet, and finally neutral if the total number N
 * 		of recipes is reached.
 * 		Every recipe is marked if it is completed or partially covered by ingredients (more than 
 * 		50% ingredients found).
 * 		Recipe has id, name, procedure, a list of ingredients, every one of them marked 
 * 		if available, not available or available from pantry.
 * 		TODOM-2: mef: take into account available measures on recipes
 * 
 * Fitness: maximize the number of filled and partial recipes, proportionally
 * 		distributed between savoury and sweet.
 * 		Formula: maximize f() = Pa + Pw, where:
 * 			X + Y = 1.0
 * 			Pa: points from savoury recipes (max Na)
 * 			Pw: points from sweet recipes (max Nw)
 * 			Point is given this way:
 * 				1.0		savoury or sweet recipe completed, even if all ingredients are in the pantry
 * 				TODOM-1: 0.8	savoury or sweet recipe completed with no pantry ingredients
 * 				0.6 	neutral replacement recipe completed
 * 				0.4		savoury or sweet recipe partially completed
 * 				0.2		neutral recipe partially completed
 * 				0.0		no points for recipes with less than 50% ingredients fulfilled
 * 
 * TODOM-8: better search on ingredients:
 * 	- semantic check or affinity between names given by user to foods, and names of recipe ingredients (e.g. tomato =~ tomatoes)
 *  - pepe don't find peperoni
 *  - plurals management
 *  - more weight to fridge foods than to pantry
 * 
 * @author red
 *
 */
public class MefConstants {
	
	// Target
	// Not set: as much as possible filled or partially filled recipes!	

	// Custom inputs
	public static final String TARGET_DESIRED_MEALS 		= "TARGET_DESIRED_MEALS"; // e.g. 10
	public static final String TARGET_SAVOURY_PROPORTION 	= "TARGET_SAVOURY_PROPORTION"; // e.g. 70
	public static final String TARGET_SWEET_PROPORTION 		= "TARGET_SWEET_PROPORTION"; // e.g. 30
	public static final String TARGET_FRIDGE_MANDATORY 		= "TARGET_FRIDGE_MANDATORY"; // e.g. true
	
	public static final String PARAM_REFRIGERATOR_FOODS 	= "REFRIGERATOR_FOODS"; // e.g. ["milk","butter"]
	public static final String PARAM_PANTRY_FOODS 			= "PANTRY_FOODS"; // e.g. ["pepper","salt"]
	public static final String PARAM_DATABASE 				= "DATABASE"; // e.g. "EN"

	public static final Integer DEFAULT_DESIRED_MEALS 		= 10;
	public static final Integer DEFAULT_SAVOURY_PROPORTION 	= 70;
	public static final Integer DEFAULT_SWEET_PROPORTION 	= 30;
	public static final Boolean DEFAULT_FRIDGE_MANDATORY 	= false;
	public static final String DEFAULT_DATABASE 			= "EN";
	
	public static final String PHENOTYPE_SAVOURY_RECIPES = 		"PHENOTYPE_SAVOURY_RECIPES";
	public static final String PHENOTYPE_SWEET_RECIPES = 		"PHENOTYPE_SWEET_RECIPES";
	public static final String PHENOTYPE_COMPLETENESS_POINTS = 	"PHENOTYPE_COMPLETENESS_POINTS";
	public static final String PHENOTYPE_PERCENTAGE_FOOD_FROM_FRIDGE ="PHENOTYPE_USED_FOOD_FROM_FRIDGE";
}