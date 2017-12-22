package org.elastxy.core.engine.operators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class RecombinatorLogicsTest {
	private final Integer[] OFF1_SIMPLE = new Integer[]{1, 2, 1, 3, 4};
	private final Integer[] OFF2_SIMPLE = new Integer[]{3, 1, 2, 4, 1};

	private final Integer[] OFF1_COMPLEX = new Integer[]{2, 1, 8, 7, 4, 1, 5, 2, 2, 6, 7, 9, 3, 3, 4, 4, 5, 1};
	private final Integer[] OFF2_COMPLEX = new Integer[]{5, 7, 6, 4, 4, 4, 1, 7, 2, 3, 1, 1, 2, 5, 3, 2, 8, 9};
	
	private Chromosome c1;
	private Chromosome c2;
	private Chromosome c3;
	
	private Chromosome cas;
	private Chromosome cbs;
	private Chromosome cac;
	private Chromosome cbc;
	

    @Before
    public void setUp(){
    	c1 = c1();
    	c2 = c2();
    	c3 = c3();
    	cas = c(OFF1_SIMPLE);
    	cbs = c(OFF2_SIMPLE);
    	cac = c(OFF1_COMPLEX);
    	cbc = c(OFF2_COMPLEX);
    }

    
    /**
     * Swaps in the middle.
     * [1, *, 2, /, 3] // [4, -, 5, +, 6] => [4, -, 2, /, 3] & [1, *, 5, +, 6]  
     */
    @Test
    public void testTwoChromosomes(){
    	RecombinatorLogics.onePointCrossover(c1.genes, c2.genes, 2);
    	assertEquals("[4, -, 2, /, 3]", c1.toAlleleList().toString());    	
    	assertEquals("[1, *, 5, +, 6]", c2.toAlleleList().toString());
    }

    /**
     * Swaps in the middle.
     * (1*2/3) (2/3*1) => (2/1*3) (1*3/2)
     */
    @Test
    public void testTwoChromosomesPreserveGenes(){
    	RecombinatorLogics.cycleCrossover(c1.genes, c3.genes, 2);
    	assertEquals("[2, *, 3, /, 1]", c1.toAlleleList().toString());
    	assertEquals("[1, /, 2, *, 3]", c3.toAlleleList().toString());    	
    }
    
    @Test
    public void testTwoChromosomesBeginningPointcut(){
    	RecombinatorLogics.onePointCrossover(c1.genes, c2.genes, 0);
    	assertEquals("[1, *, 2, /, 3]", c1.toAlleleList().toString());
    	assertEquals("[4, -, 5, +, 6]", c2.toAlleleList().toString());    	
    }


    @Test
    public void testTwoChromosomesBeginningPointcutPreserveGenes(){
    	RecombinatorLogics.cycleCrossover(c1.genes, c3.genes, 0);
    	assertEquals("[2, *, 3, /, 1]", c1.toAlleleList().toString());
    	assertEquals("[1, /, 2, *, 3]", c3.toAlleleList().toString());    	
    }


    @Test
    public void testTwoChromosomesEndingPointcut(){
    	RecombinatorLogics.onePointCrossover(c1.genes, c2.genes, c1.genes.size()-1);
    	assertEquals("[4, -, 5, +, 3]", c1.toAlleleList().toString());    	
    	assertEquals("[1, *, 2, /, 6]", c2.toAlleleList().toString());
    }

    // (3*2/|1) (2/1*|3)
    @Test
    public void testTwoChromosomesEndingPointcutPreserveGenes(){
    	RecombinatorLogics.cycleCrossover(c1.genes, c3.genes, c1.genes.size()-1);
    	assertEquals("[2, *, 3, /, 1]", c1.toAlleleList().toString());    	
    	assertEquals("[1, /, 2, *, 3]", c3.toAlleleList().toString());
    }

    @Test
    public void testDuplicatesSimpleStart() throws IllegalSolutionException {
    	RecombinatorLogics.cycleCrossover(cas.genes, cbs.genes, 0);
    	checkGenes(cas.genes, OFF1_SIMPLE);
    	checkGenes(cbs.genes, OFF1_SIMPLE);
    	assertEquals("[3, 2, 1, 4, 1]", cas.toAlleleList().toString());  
    	assertEquals("[1, 1, 2, 3, 4]", cbs.toAlleleList().toString());
    }

    @Test
    public void testDuplicatesSimpleEnd() throws IllegalSolutionException {
    	RecombinatorLogics.cycleCrossover(cas.genes, cbs.genes, cbs.genes.size()-1);
    	checkGenes(cas.genes, OFF1_SIMPLE);
    	checkGenes(cbs.genes, OFF1_SIMPLE);
    	assertEquals("[3, 2, 1, 4, 1]", cas.toAlleleList().toString());  
    	assertEquals("[1, 1, 2, 3, 4]", cbs.toAlleleList().toString());
    }

    @Test
    public void testDuplicatesSimpleMiddle() throws IllegalSolutionException {
    	RecombinatorLogics.cycleCrossover(cas.genes, cbs.genes, 2);
    	checkGenes(cas.genes, OFF1_SIMPLE);
    	checkGenes(cbs.genes, OFF1_SIMPLE);
    	assertEquals("[1, 1, 2, 3, 4]", cas.toAlleleList().toString());  
    	assertEquals("[3, 2, 1, 4, 1]", cbs.toAlleleList().toString());
    }

//    4,5,7,3,7,2,1,3,5,2,8,4,4,1,2,1,6,9
//    4,1,6,3,2,1,7,8,9,4,1,2,7,4,5,3,5,2

    // Swap from first pos
    @Test
    public void testDuplicatesComplexStart() throws IllegalSolutionException {
    	RecombinatorLogics.cycleCrossover(cac.genes, cbc.genes, 0);
    	checkGenes(cac.genes, OFF1_COMPLEX);
    	checkGenes(cbc.genes, OFF1_COMPLEX);
    	String off1 = "[5, 7, 8, 4, 4, 4, 1, 7, 2, 6, 1, 1, 2, 3, 3, 2, 5, 9]";
    	String off2 = "[2, 1, 6, 7, 4, 1, 5, 2, 2, 3, 7, 9, 3, 5, 4, 4, 8, 1]";
    	assertEquals(off1, cac.toAlleleList().toString());
    	assertEquals(off2, cbc.toAlleleList().toString());  
    }

    
  // Swap from end pos
  @Test
  public void testDuplicatesComplexEnd() throws IllegalSolutionException {
  	RecombinatorLogics.cycleCrossover(cac.genes, cbc.genes, cbc.genes.size()-1);
  	checkGenes(cac.genes, OFF1_COMPLEX);
  	checkGenes(cbc.genes, OFF1_COMPLEX);
  	String off1 = "[2, 1, 6, 7, 4, 1, 5, 2, 2, 3, 7, 1, 3, 5, 4, 4, 8, 9]";
  	String off2 = "[5, 7, 8, 4, 4, 4, 1, 7, 2, 6, 1, 9, 2, 3, 3, 2, 5, 1]";
  	assertEquals(off1, cac.toAlleleList().toString());  
  	assertEquals(off2, cbc.toAlleleList().toString());
  }

  
	// Swap from middle pos
    @Test
    public void testDuplicatesComplexMiddle() throws IllegalSolutionException {
    	cac = c(OFF1_COMPLEX);
    	cbc = c(OFF2_COMPLEX);
    	RecombinatorLogics.cycleCrossover(cac.genes, cbc.genes, 8);
    	checkGenes(cac.genes, OFF1_COMPLEX);
    	checkGenes(cbc.genes, OFF1_COMPLEX);
    	String off1 = "[5, 7, 8, 4, 4, 4, 1, 7, 2, 6, 1, 1, 2, 3, 3, 2, 5, 9]";
    	String off2 = "[2, 1, 6, 7, 4, 1, 5, 2, 2, 3, 7, 9, 3, 5, 4, 4, 8, 1]";
    	assertEquals(off1, cac.toAlleleList().toString());
    	assertEquals(off2, cbc.toAlleleList().toString());  
    }

    
    private Chromosome c1(){
    	Chromosome result = new Chromosome();
    	result.genes.add(g(1));
    	result.genes.add(g('*'));
    	result.genes.add(g(2));
    	result.genes.add(g('/'));
    	result.genes.add(g(3));
    	return result;
    }
   
    private Chromosome c2(){
    	Chromosome result = new Chromosome();
    	result.genes.add(g(4));
    	result.genes.add(g('-'));
    	result.genes.add(g(5));
    	result.genes.add(g('+'));
    	result.genes.add(g(6));
    	return result;
    }
   
    private Chromosome c3(){
    	Chromosome result = new Chromosome();
    	result.genes.add(g(2));
    	result.genes.add(g('/'));
    	result.genes.add(g(3));
    	result.genes.add(g('*'));
    	result.genes.add(g(1));
    	return result;
    }
    
    private Chromosome c(Integer[] ints){
    	Chromosome result = new Chromosome();
    	result.genes = new ArrayList<Gene>();
    	for(int i : ints) result.genes.add(g(i));
    	return result;
    }
    
    private Gene g(int x){
    	Gene result = new Gene();
    	Allele<Integer> allele = new Allele<Integer>();
    	allele.value = x;
    	result.allele = allele;
    	return result;
    }
    
    private Gene g(char x){
    	Gene result = new Gene();
    	Allele<Character> allele = new Allele<Character>();
    	allele.value = x;
    	result.allele = allele;
    	return result;
    }
    

	public static void checkGenes(List<Gene> genes, Integer[] SAMPLE) throws IllegalSolutionException {
		List<Integer> check = genes.stream().map(g -> (Integer)g.allele.value).collect(Collectors.toList());
		check(check, SAMPLE);
	}
	
	public static void checkAlleles(List<Allele> alleles, Integer[] SAMPLE) throws IllegalSolutionException {
		List<Integer> check = alleles.stream().map(a -> (Integer)a.value).collect(Collectors.toList());
		check(check, SAMPLE);
	}
	
	private static void check(List<Integer> check, Integer[] SAMPLE) throws IllegalSolutionException {
		List<Integer> trial = new ArrayList(Arrays.asList(SAMPLE));
		List<Integer> copyOfTrial = new ArrayList(trial);
		for(Integer c : check) trial.remove(c); 
		if(!trial.isEmpty()){
			System.out.println("ERROR");
			throw new IllegalSolutionException("Bad solution after swap.");
		}
		for(Integer t : copyOfTrial) check.remove(t); 
		if(!check.isEmpty()){
			System.out.println("ERROR");
			throw new IllegalSolutionException("Bad solution after swap.");
		}
	}
}
