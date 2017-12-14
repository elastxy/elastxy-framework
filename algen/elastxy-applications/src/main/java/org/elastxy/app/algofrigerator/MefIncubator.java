package org.elastxy.app.algofrigerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elastxy.app.algofrigerator.data.IngredientsCoverage;
import org.elastxy.app.algofrigerator.data.MefWorkingDataset;
import org.elastxy.app.algofrigerator.data.Recipe;
import org.elastxy.app.algofrigerator.data.RecipeType;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.domain.genetics.phenotype.ComplexPhenotype;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.elastxy.core.engine.fitness.Incubator;

public class MefIncubator implements Incubator<Strand, ComplexPhenotype>{
	private static final RecipeCompletenessComparator RECIPE_COMPARATOR = new RecipeCompletenessComparator();

	
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
	public ComplexPhenotype grow(Strand genotype, Env env) throws IllegalSolutionException {
		
		// Create recipes by type
		RecipeAccumulator accumulator = new RecipeAccumulator();
		accumulator.calculate((MefWorkingDataset)env.workingDataset, genotype, (MefGoal)env.target.getGoal());
	
		// Create phenotype
		ComplexPhenotype result = new ComplexPhenotype();
		result.value.put(MefConstants.PHENOTYPE_SAVOURY_RECIPES, 		accumulator.resultingRecipes.get(RecipeType.SAVOURY));
		result.value.put(MefConstants.PHENOTYPE_SWEET_RECIPES, 		accumulator.resultingRecipes.get(RecipeType.SWEET));
		result.value.put(MefConstants.PHENOTYPE_COMPLETENESS_POINTS, 	accumulator.resultingCompleteness);
		result.value.put(MefConstants.PHENOTYPE_PERCENTAGE_FOOD_FROM_FRIDGE, 	accumulator.percentageFoodsFromFridge);
		return result;
	}
	
	

	
	private class RecipeAccumulator {
		
		// Intermediate calc
		private List<Recipe> allRecipes;
		
		// Results
		private Map<RecipeType, List<Recipe>> resultingRecipes = new HashMap<RecipeType, List<Recipe>>();
		private double resultingCompleteness = 0.0;
		private double percentageFoodsFromFridge = 0.0;
		
		
		/**
		 * 
		 * @param genotype
		 * @param goal
		 * @return
		 */
		public void calculate(MefWorkingDataset workingDataset, Strand genotype, MefGoal goal) throws IllegalSolutionException {
			resultingRecipes.put(RecipeType.SAVOURY, new ArrayList<Recipe>());
			resultingRecipes.put(RecipeType.SWEET, new ArrayList<Recipe>());
			
			// Convert Genes to Recipes and calculate coverage
			allRecipes = new ArrayList<Recipe>();
			for(int c=0; c <=2; c++){
				allRecipes.addAll(convertGeneToRecipe(workingDataset, c, genotype, goal));
			}
			
			// Sort by completeness DESC
			Collections.sort(allRecipes, RECIPE_COMPARATOR);
			
			// Select recipes to a maximum number
			int usedFoodsFromFridge = 0;
			int totalIngredients = 0;
			Iterator<Recipe> it = allRecipes.iterator();
			while(it.hasNext()){
				Recipe r = it.next();
				
				// Points from completeness
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
				
				// Presence of fridge foods in the recipe
				usedFoodsFromFridge += r.ackFridgeIngredients.size();
				totalIngredients += r.ingredients.size();
			}
			
			percentageFoodsFromFridge = (double)usedFoodsFromFridge / (double)totalIngredients;
		}
		
		
		/**
		 * Convert Gene to a Recipe, checking also that the coverage is given before
		 * @param chromosome
		 * @param genotype
		 * @param goal
		 * @return
		 */
		private List<Recipe> convertGeneToRecipe(MefWorkingDataset workingDataset, int chromosome, Strand genotype, MefGoal goal) throws IllegalSolutionException {
			Iterator<Gene> genes = genotype.chromosomes.get(chromosome).genes.iterator();
			List<Long> recipesIds = new ArrayList<Long>();
			while(genes.hasNext()){
				Gene gene = genes.next();
				recipesIds.add((Long)gene.allele.value);
			}
			List<Recipe> recipes = workingDataset.getRecipes(recipesIds);
			for(int r=0; r < recipes.size(); r++){
				Recipe recipe = recipes.get(r);
				if(recipe.coverage == null || recipe.coverage == IngredientsCoverage.UNDEFINED){
					String legalCheck = "Cannot convert Gene to Recipe. No coverage found ("+recipe.coverage+") for recipe id: "+recipe.id;
					throw new IllegalSolutionException(legalCheck, legalCheck);
				}
//				MefUtils.checkCoverage(recipes.get(r), goal.refrigeratorFoods);
			}
			return recipes;
		}
	
	}
	
	
}
