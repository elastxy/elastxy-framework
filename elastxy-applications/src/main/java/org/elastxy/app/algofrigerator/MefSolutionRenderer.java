package org.elastxy.app.algofrigerator;

import java.util.List;

import org.apache.log4j.Logger;
import org.elastxy.app.algofrigerator.data.Recipe;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.phenotype.ComplexPhenotype;
import org.elastxy.core.tracking.SolutionRenderer;

public class MefSolutionRenderer implements SolutionRenderer<String> {
	public static Logger logger = Logger.getLogger(MefSolutionRenderer.class);
	
			
	@Override
	public String render(Solution solution){
		ComplexPhenotype phenotype = (ComplexPhenotype)solution.getPhenotype();
		if(phenotype==null){
			logger.error("Cannot render solution. Phenotype null for solution: "+solution);
			return "No phenotype to visualize.";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("~ ~ ~ MENU ~ ~ ~");
		sb.append("\n");
		
		// Print recipes
		printRecipes(sb, phenotype, MefConstants.PHENOTYPE_SAVOURY_RECIPES, "SAVOURY");
		sb.append("\n");

		printRecipes(sb, phenotype, MefConstants.PHENOTYPE_SWEET_RECIPES, "SWEET");
		sb.append("\n");
		
		// Print completeness
		double completeness = (double)phenotype.getValue().get(MefConstants.PHENOTYPE_COMPLETENESS_POINTS);
		sb.append(String.format("-> Algorifero completeness: %.3f%n%n", completeness));
		sb.append("\n");
		
		// Print solution
		sb.append("SOLUTION: "+solution+"\n");
		
		return sb.toString();
	}


	private void printRecipes(StringBuffer sb, ComplexPhenotype phenotype, String type, String typeDescr) {
		List<Recipe> recipes = (List<Recipe>)phenotype.getValue().get(type);
		sb.append(String.format("%n%s RECIPES [%d]%n", typeDescr, recipes.size()));
		int tot = recipes.size();
		for(int r=0; r < tot; r++){
			Recipe recipe = recipes.get(r);
			sb.append(String.format("%d> %s (Coverage:%s)%n", (r+1), recipe.name, recipe.coverage));
			sb.append("    Ingredients:\n");
			for(String i : recipe.ingredients){
				sb.append(String.format("    . %s%n", i));
			}
			sb.append("    Available:\n");
			for(String a : recipe.available){
				sb.append(String.format("    . %s%n", a));
			}
			sb.append("    Persons: "+recipe.persons+"\n");
			sb.append("    Note: "+recipe.note+"\n");
			sb.append("    Preparation:\n");
			sb.append(recipe.preparation+"\n");
		}
	}
	
}
