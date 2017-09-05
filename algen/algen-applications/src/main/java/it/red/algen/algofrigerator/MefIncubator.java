package it.red.algen.algofrigerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.red.algen.algofrigerator.data.IngredientsCoverage;
import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.algofrigerator.data.RecipeType;
import it.red.algen.algofrigerator.data.RecipesDatabase;
import it.red.algen.algofrigerator.data.RecipesDatabaseCSV;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.ChromosomeGenotype;
import it.red.algen.domain.genetics.ComplexPhenotype;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.engine.Incubator;

public class MefIncubator implements Incubator<ChromosomeGenotype, ComplexPhenotype>{
	private static final RecipeCompletenessComparator RECIPE_COMPARATOR = new RecipeCompletenessComparator();
	
	
	// TODOM: inject
	private RecipesDatabase db = new RecipesDatabaseCSV();
	
	/**
	 * Solution grows to a complete list of recipe of two kind, savoury and sweet.
	 * 
	 * Incubator starts from the genotype representing recipes id, and creates
	 * the two lists retrieving the best of all recipes, in this order:
	 * - all complete savoury recipes, up to Na
	 * - all complete sweet recipes, up to Nw
	 * - fills the remaining with neutral complete, up to Nn
	 * - all partially incomplete: savoury, then sweet, finally neutral
	 * 
	 * A number of meals could be empty.
	 * 
	 */	
	@Override
	public ComplexPhenotype grow(ChromosomeGenotype genotype, Env env) {

		// Create recipes by type
		RecipeAccumulator accumulator = new RecipeAccumulator();
		accumulator.calculate(genotype, (MefGoal)env.target.getGoal());
	
		// Create phenotype
		ComplexPhenotype result = new ComplexPhenotype();
		result.value.put(MefApplication.PHENOTYPE_SAVOURY_RECIPES, 		accumulator.resultingRecipes.get(RecipeType.SAVOURY));
		result.value.put(MefApplication.PHENOTYPE_SWEET_RECIPES, 		accumulator.resultingRecipes.get(RecipeType.SWEET));
		result.value.put(MefApplication.PHENOTYPE_COMPLETENESS_POINTS, 	accumulator.resultingCompleteness);
		return result;
	}
	
	

	
	private class RecipeAccumulator {
		
		// Intermediate calc
		private List<Recipe> allRecipes;
		
		// Results
		private Map<RecipeType, List<Recipe>> resultingRecipes = new HashMap<RecipeType, List<Recipe>>();
		private double resultingCompleteness = 0.0;
		
		
		/**
		 * 
		 * @param genotype
		 * @param goal
		 * @return
		 */
		public void calculate(ChromosomeGenotype genotype, MefGoal goal){
			resultingRecipes.put(RecipeType.SAVOURY, new ArrayList<Recipe>());
			resultingRecipes.put(RecipeType.SWEET, new ArrayList<Recipe>());
			
			// Convert Genes to Recipes and calculate coverage
			allRecipes = new ArrayList<Recipe>();
			for(int c=0; c <=2; c++){
				allRecipes.addAll(convertGeneToRecipe(c, genotype, goal));
			}
			
			// Sort by completeness DESC
			Collections.sort(allRecipes, RECIPE_COMPARATOR);
			
			// Select recipes to a maximum number
			Iterator<Recipe> it = allRecipes.iterator();
			while(it.hasNext()){
				Recipe r = it.next();
				if(r.recipeType==RecipeType.SAVOURY && resultingRecipes.get(RecipeType.SAVOURY).size() < goal.savouryMeals){
					resultingRecipes.get(RecipeType.SAVOURY).add(r);
					resultingCompleteness += r.coverage.getPoints();
				}
				else if(r.recipeType==RecipeType.SWEET && resultingRecipes.get(RecipeType.SWEET).size() < goal.sweetMeals){
					resultingRecipes.get(RecipeType.SWEET).add(r);
					resultingCompleteness += r.coverage.getPoints();
				}
				else if(r.recipeType==RecipeType.NEUTRAL 
						&& resultingRecipes.get(RecipeType.SAVOURY).size() < goal.sweetMeals){
					resultingRecipes.get(RecipeType.SAVOURY).add(r);
					resultingCompleteness += r.coverage.getPoints();
				}
				else if(r.recipeType==RecipeType.NEUTRAL 
						&& resultingRecipes.get(RecipeType.SWEET).size() < goal.sweetMeals){
					resultingRecipes.get(RecipeType.SWEET).add(r);
					resultingCompleteness += r.coverage.getPoints();
				}
			}
		}
		
		
		/**
		 * Convert Gene to a Recipe, calculating also the coverage
		 * @param chromosome
		 * @param genotype
		 * @param goal
		 * @return
		 */
		private List<Recipe> convertGeneToRecipe(int chromosome, ChromosomeGenotype genotype, MefGoal goal) {
			Iterator<Gene> genes = genotype.chromosomes.get(chromosome).genes.iterator();
			List<Long> recipesIds = new ArrayList<Long>();
			while(genes.hasNext()){
				Gene gene = genes.next();
				recipesIds.add((Long)gene.allele.value);
			}
			List<Recipe> recipes = db.getRecipes(recipesIds);
			for(int r=0; r < recipes.size(); r++){
				MefUtils.checkCoverage(recipes.get(r), goal.refrigeratorFoods);
			}
			return recipes;
		}
	
	}
	
	
}
