package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.red.algen.dataaccess.AlleleValuesProvider;
import it.red.algen.dataaccess.InMemoryAlleleValuesProvider;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.genetics.PredefinedGenomaBuilder;
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
     * Genoma1: One chromosome of 5 positions of 3 values each.
     * Genoma2: One chromosome of 3 positions respectively of 3,2,1 values.
     */
    public void setUp(){
    	createGenoma1();
    	createGenoma2();
    }

	private void createGenoma1() {

		// 3 possible alleles
		List<Allele> predefinedAlleles = new ArrayList<Allele>();
		for(int i=0; i < 3; i++){
			Allele<Integer> allele = new Allele<Integer>();
			allele.value = i;
			predefinedAlleles.add(allele);
		}
		AlleleValuesProvider provider = new InMemoryAlleleValuesProvider();
		provider.insertAlleles(predefinedAlleles);

		// 5 positions/genes
		genoma1 = PredefinedGenomaBuilder.build(5, provider, false);
	}
	

	private void createGenoma2() {
		AlleleValuesProvider provider = new InMemoryAlleleValuesProvider();
		provider.insertAlleles("0", Arrays.<Allele>asList(new Allele<Integer>(17),		new Allele<Integer>(18),	new Allele<Integer>(19)));
		provider.insertAlleles("1", Arrays.<Allele>asList(new Allele<Character>('a'),	new Allele<Character>('b')));
		provider.insertAlleles("2", Arrays.<Allele>asList(new Allele<Double>(4.5)));
		genoma2 = PredefinedGenomaBuilder.build(3, provider, false);
	}
    

    public void testStructureGenoma1(){
    	assertEquals(1, genoma1.getGenotypeStructure().getNumberOfChromosomes());
    	assertEquals(5, genoma1.getGenotypeStructure().getNumberOfGenes(0)); 

    	assertEquals(5, genoma1.getGenotypeStructure().getPositionsSize());
    	assertEquals(Arrays.asList("0","1","2","3","4"),genoma1.getGenotypeStructure().getPositions());
    }
    
    public void testMethodsGenoma1(){
    	assertEquals(5, genoma1.getOrderedAlleles().size());
    	assertEquals(5, genoma1.getRandomAlleles().size());
    	assertEquals(5, genoma1.getRandomAllelesAsMap().size());
    	assertTrue(Arrays.asList(0,1,2).contains(genoma1.getRandomAllele("3").value));
    	assertEquals(3, genoma1.getRandomAlleles(Arrays.asList("0","4","2")).size());
    }

    
    public void testStructureGenoma2(){
    	assertEquals(1, genoma2.getGenotypeStructure().getNumberOfChromosomes());
    	assertEquals(3, genoma2.getGenotypeStructure().getNumberOfGenes(0)); 
    	
    	assertEquals(3, genoma2.getGenotypeStructure().getPositionsSize());
    	assertEquals(Arrays.asList("0","1","2"),genoma2.getGenotypeStructure().getPositions());
    }
    
    public void testMethodsGenoma2(){
    	try { assertEquals(3, genoma2.getOrderedAlleles().size()); fail(); } catch(Exception ex){}
    	try { assertEquals(3, genoma2.getRandomAlleles()); } catch(Exception ex){}
    	try { assertEquals(3, genoma2.getRandomAllelesAsMap()); } catch(Exception ex){}
    	assertTrue(Arrays.asList(17,18,19).contains(genoma2.getRandomAllele("0").value));
    	assertTrue(Arrays.asList('a','b').contains(genoma2.getRandomAllele("1").value));
    	assertTrue(Arrays.asList(4.5).contains(genoma2.getRandomAllele("2").value));
    	assertEquals(2, genoma2.getRandomAlleles(Arrays.asList("0","2")).size());
    }
    
}
