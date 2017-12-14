package it.red.algen.algofrigerator;

import java.util.Comparator;

import it.red.algen.algofrigerator.data.Recipe;

/**
 * List recipes in order of completeness:
 * COMPLETE SA|SW
 * COMPLETE NE
 * PARTIAL 	SA|SW
 * PARTIAL 	NE
 * NONE 	SA|SW
 * NONE		NE
 * @author red
 *
 */
public class RecipeCompletenessComparator implements Comparator<Recipe> {

	@Override
	public int compare(Recipe r1, Recipe r2) {
	    int result = r1.coverage.order().compareTo(r2.coverage.order());
	    if (result != 0) return result;

	    result = r1.recipeType.order().compareTo(r2.recipeType.order());
	    if (result != 0) return result;
	    
	    result = Long.compare(r1.id, r2.id);
		
//		int result = Comparator.comparingInt((Recipe r) -> r.)
//          .thenComparingInt(r->r.recipeType)
//          .thenComparingLong(r->r.id)
//          .compare(r1, r2);
		return result;
	}
	
}