package it.red.algen.domain.genetics;

import it.red.algen.engine.genetics.GenomaPositionComparator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class PositionsComparatorTest 
    extends TestCase
{
	
	private GenomaPositionComparator comparator = new GenomaPositionComparator();

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PositionsComparatorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PositionsComparatorTest.class );
    }

    
    public void testSequence(){
    	// Equality
    	assertEquals(0, comparator.compare("5", "5"));
    	
    	// Unequality
    	assertTrue(comparator.compare("3", "5") < 0);
    	assertTrue(comparator.compare("5", "3") > 0);
    }
    
    public void testStrand(){
    	// Equality
    	assertEquals(0, comparator.compare("7.5", "7.5"));

    	// Unequality
    	assertTrue(comparator.compare("7.4", "7.5") < 0);
    	assertTrue(comparator.compare("7.5", "7.4") > 0);
    	assertTrue(comparator.compare("6.5", "7.5") < 0);
    	assertTrue(comparator.compare("7.5", "6.5") > 0);
    }

    public void testDoubleStrand(){
    	// Equality
    	assertEquals(0, comparator.compare("2.7.5", "2.7.5"));
    	
    	// Unequality
    	assertTrue(comparator.compare("2.7.4", "2.7.5") < 0);
    	assertTrue(comparator.compare("2.7.5", "2.7.4") > 0);
    	assertTrue(comparator.compare("2.6.5", "2.7.5") < 0);
    	assertTrue(comparator.compare("2.7.5", "2.6.5") > 0);
    	assertTrue(comparator.compare("1.6.5", "2.7.5") < 0);
    	assertTrue(comparator.compare("2.7.5", "1.6.5") > 0);
    }
}
