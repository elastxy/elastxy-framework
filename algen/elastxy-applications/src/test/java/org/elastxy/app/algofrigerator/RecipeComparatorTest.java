package org.elastxy.app.algofrigerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.elastxy.app.algofrigerator.RecipeCompletenessComparator;
import org.elastxy.app.algofrigerator.data.IngredientsCoverage;
import org.elastxy.app.algofrigerator.data.Recipe;
import org.elastxy.app.algofrigerator.data.RecipeType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RecipeComparatorTest 
    extends TestCase
{
	
	private RecipeCompletenessComparator comparator = new RecipeCompletenessComparator();
	private List<Recipe> recipes;

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RecipeComparatorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RecipeComparatorTest.class );
    }

    
    
    public void setUp(){
    	recipes = Arrays.asList(
    			createRecipe(3,RecipeType.SWEET,IngredientsCoverage.FULL),
    			createRecipe(17,RecipeType.NEUTRAL,IngredientsCoverage.NONE),
    			createRecipe(12,RecipeType.NEUTRAL,IngredientsCoverage.PARTIAL),
    			createRecipe(14,RecipeType.SAVOURY,IngredientsCoverage.NONE),
    			createRecipe(16,RecipeType.SWEET,IngredientsCoverage.NONE),
    			createRecipe(10,RecipeType.SWEET,IngredientsCoverage.PARTIAL),
    			createRecipe(4,RecipeType.SWEET,IngredientsCoverage.FULL),
    			createRecipe(1,RecipeType.SAVOURY,IngredientsCoverage.FULL),
    			createRecipe(2,RecipeType.SAVOURY,IngredientsCoverage.FULL),
    			createRecipe(8,RecipeType.SAVOURY,IngredientsCoverage.PARTIAL),
    			createRecipe(6,RecipeType.NEUTRAL,IngredientsCoverage.FULL),
    			createRecipe(13,RecipeType.SAVOURY,IngredientsCoverage.NONE),
    			createRecipe(7,RecipeType.SAVOURY,IngredientsCoverage.PARTIAL),
    			createRecipe(5,RecipeType.NEUTRAL,IngredientsCoverage.FULL),
    			createRecipe(18,RecipeType.NEUTRAL,IngredientsCoverage.NONE),
    			createRecipe(15,RecipeType.SWEET,IngredientsCoverage.NONE),
    			createRecipe(9,RecipeType.SWEET,IngredientsCoverage.PARTIAL),
    			createRecipe(11,RecipeType.NEUTRAL,IngredientsCoverage.PARTIAL)
    			);	
    }
    
    private static Recipe createRecipe(int id, RecipeType type, IngredientsCoverage coverage){
    	Recipe result = new Recipe();
    	result.id = (long)id;
    	result.recipeType = type;
    	result.coverage = coverage;
    	return result;
    }
    
    
    
    
    public void testTypicalList(){
    	Collections.sort(recipes, comparator);
    	
    	// Equality
    	for(long r=1; r <=18; r++ ){
    		assertEquals(r, (long)recipes.get((int)r-1).id);
    	}
    }
}
