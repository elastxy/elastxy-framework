package it.red.algen.algofrigerator.data;

import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RecipeDatabaseTest 
    extends TestCase
{
	
	private RecipesDatabaseCSV reader = new RecipesDatabaseCSV();

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RecipeDatabaseTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RecipeDatabaseTest.class );
    }

    
    
    public void setUp(){
    }
    
    public void testReadAll(){
    	List<Recipe> recipes = reader.getAllRecipes();
    	
    	assertEquals(22, recipes.size());
    	
    	Recipe first = recipes.get(0);
    	assertEquals(1L, (long)first.id);
    	assertEquals("pasta al sugo", first.name);
    	assertEquals(RecipeType.SAVOURY, first.recipeType);
    	assertEquals(
    			Arrays.asList("tomatoes","onion","oil","parmesan","pasta","salt").toString(), 
    			first.ingredients.toString());
    }


    public void testReadSome(){
    	List<Recipe> recipes = reader.getRecipes(Arrays.asList(5L,2L,14L));
    	assertEquals(3, recipes.size());
    	
    	recipes = reader.getRecipes(Arrays.asList(1L,22L));
    	assertEquals(2, recipes.size());
    	assertEquals(1L, (long)recipes.get(0).id);
    	assertEquals(22L, (long)recipes.get(1).id);
    }

}
