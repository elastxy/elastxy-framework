package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.PredefinedGenoma;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class PredefinedGenomaTest 
    extends TestCase
{

	private PredefinedGenoma genoma;

	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PredefinedGenomaTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PredefinedGenomaTest.class );
    }

    /**
     * Two chromosome of 5 and 4 positions each
     */
    public void setUp(){
    	Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();
		List<Allele> predefinedAlleles = new ArrayList<Allele>();
		for(int i=0; i < 3; i++){
			Allele<Integer> allele = new Allele<Integer>();
			allele.value = i;
			predefinedAlleles.add(allele);
		}
		for(int i=0; i < 5; i++) {
			alleles.put(String.valueOf(i), predefinedAlleles);
		}
		genoma = new PredefinedGenoma();
		genoma.initialize(alleles);
    }
    
    
    public void testPositions(){
    	assertEquals(5, genoma.getPositionsSize());
    	assertEquals(Arrays.asList("0","1","2","3","4"),genoma.getPositions());
    }

    
    public void testElementsCount(){
    	assertEquals(1, genoma.getNumberOfChromosomes());
    	assertEquals(5, genoma.getNumberOfGenes(0)); 
    }
    
}
