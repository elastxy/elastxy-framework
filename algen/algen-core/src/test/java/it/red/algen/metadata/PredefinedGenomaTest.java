package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.domain.genetics.PredefinedGenoma;
import it.red.algen.domain.genetics.PredefinedGenomaBuilder;
import it.red.algen.domain.genetics.genotype.Allele;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class PredefinedGenomaTest 
    extends TestCase
{

	private PredefinedGenoma genoma1;
	private PredefinedGenoma genoma2;

	
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
     * One chromosome of 5 positions of 3 values each.
     */
    public void setUp(){
    	Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();
    	
    	// 3 possible alleles
		List<Allele> predefinedAlleles = new ArrayList<Allele>();
		for(int i=0; i < 3; i++){
			Allele<Integer> allele = new Allele<Integer>();
			allele.value = i;
			predefinedAlleles.add(allele);
		}
		
		// 5 genes positions in chromosome
		for(int i=0; i < 5; i++) {
			alleles.put(String.valueOf(i), predefinedAlleles);
		}
		
		genoma1 = PredefinedGenomaBuilder.build(5, predefinedAlleles, false);
		genoma2 = PredefinedGenomaBuilder.build(alleles, false);
    }
    

    public void testPositions(){
    	_testPositions(genoma1);
    	_testPositions(genoma2);
    }

    public void _testPositions(PredefinedGenoma genoma){
    	assertEquals(5, genoma.getGenotypeStructure().getPositionsSize());
    	assertEquals(Arrays.asList("0","1","2","3","4"),genoma.getGenotypeStructure().getPositions());
    }

    
    public void testElementsCount(){
    	_testElementsCount(genoma1);
    	_testElementsCount(genoma2);
    }
    
    public void _testElementsCount(PredefinedGenoma genoma){
    	assertEquals(1, genoma.getGenotypeStructure().getNumberOfChromosomes());
    	assertEquals(5, genoma.getGenotypeStructure().getNumberOfGenes(0)); 
    }
    
}
