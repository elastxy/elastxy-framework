package it.red.algen.algofrigerator;

import java.util.List;

import org.apache.log4j.Logger;

import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.ComplexPhenotype;
import it.red.algen.tracking.SolutionRenderer;

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
		printRecipes(sb, phenotype, MefApplication.PHENOTYPE_SAVOURY_RECIPES, "SAVOURY");
		sb.append("\n");

		printRecipes(sb, phenotype, MefApplication.PHENOTYPE_SWEET_RECIPES, "SWEET");
		sb.append("\n");
		
		// Print completeness
		double completeness = (double)phenotype.getValue().get(MefApplication.PHENOTYPE_COMPLETENESS_POINTS);
		sb.append(String.format("-> Algorifero completeness: %.3f%n%n", completeness));
		sb.append("\n");
		
		// Print solution
		sb.append("SOLUTION: "+solution+"\n");
		return sb.toString();
	}


	private void printRecipes(StringBuffer sb, ComplexPhenotype phenotype, String type, String typeDescr) {
		List<Recipe> recipes = (List<Recipe>)phenotype.getValue().get(type);
		sb.append(String.format("%s RECIPES [%d]%n", typeDescr, recipes.size()));
		for(int r=0; r < recipes.size(); r++){
			Recipe recipe = recipes.get(r);
			sb.append(String.format("%d> %s (Coverage:%s)%n", (r+1), recipe.name, recipe.coverage));
			sb.append("    Ingredients:\n");
			for(int i=0; i < recipe.ingredients.size();i++){
				sb.append(String.format("    . %s%n", recipe.ingredients.get(i)));
			}
			sb.append("    Available:\n");
			for(int i=0; i < recipe.available.size();i++){
				sb.append(String.format("    . %s%n", recipe.available.get(i)));
			}
		}
	}
	
}
