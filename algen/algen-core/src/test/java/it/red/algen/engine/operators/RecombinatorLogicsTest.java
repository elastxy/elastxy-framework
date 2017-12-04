package it.red.algen.engine.operators;

import org.junit.Before;
import org.junit.Test;

import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.genotype.Gene;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class RecombinatorLogicsTest {
	private Chromosome c1;
	private Chromosome c2;
	private Chromosome c3;
	

    @Before
    public void setUp(){
    	c1 = c1();
    	c2 = c2();
    	c3 = c3();
    }

    
    /**
     * Swaps in the middle.
     * [1, *, 2, /, 3] // [4, -, 5, +, 6] => [4, -, 2, /, 3] & [1, *, 5, +, 6]  
     */
    @Test
    public void testTwoChromosomes(){
    	RecombinatorLogics.cutAndSwapSequence(c1.genes, c2.genes, 2);
    	assertEquals("[4, -, 2, /, 3]", c1.toAlleleList().toString());    	
    	assertEquals("[1, *, 5, +, 6]", c2.toAlleleList().toString());
    }

    /**
     * Swaps in the middle.
     * (1*2/3) (2/3*1) => (2/1*3) (1*3/2)
     */
    @Test
    public void testTwoChromosomesPreserveGenes(){
    	RecombinatorLogics.cutAndRedistributeSequence(c1.genes, c3.genes, 2);
    	assertEquals("[1, /, 2, *, 3]", c1.toAlleleList().toString());    	
    	assertEquals("[2, *, 3, /, 1]", c3.toAlleleList().toString());
    }
    
    @Test
    public void testTwoChromosomesBeginningPointcut(){
    	RecombinatorLogics.cutAndSwapSequence(c1.genes, c2.genes, 0);
    	assertEquals("[1, *, 2, /, 3]", c1.toAlleleList().toString());
    	assertEquals("[4, -, 5, +, 6]", c2.toAlleleList().toString());    	
    }


    @Test
    public void testTwoChromosomesBeginningPointcutPreserveGenes(){
    	RecombinatorLogics.cutAndRedistributeSequence(c1.genes, c3.genes, 0);
    	assertEquals("[1, /, 2, *, 3]", c1.toAlleleList().toString());    	
    	assertEquals("[2, *, 3, /, 1]", c3.toAlleleList().toString());
    }


    @Test
    public void testTwoChromosomesEndingPointcut(){
    	RecombinatorLogics.cutAndSwapSequence(c1.genes, c2.genes, c1.genes.size()-1);
    	assertEquals("[4, -, 5, +, 3]", c1.toAlleleList().toString());    	
    	assertEquals("[1, *, 2, /, 6]", c2.toAlleleList().toString());
    }

    // (3*2/|1) (2/1*|3)
    @Test
    public void testTwoChromosomesEndingPointcutPreserveGenes(){
    	RecombinatorLogics.cutAndRedistributeSequence(c1.genes, c3.genes, c1.genes.size()-1);
    	assertEquals("[1, /, 2, *, 3]", c1.toAlleleList().toString());
    	assertEquals("[2, *, 3, /, 1]", c3.toAlleleList().toString());    	
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
}
