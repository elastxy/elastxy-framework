package it.red.algen.engine.metadata;

import java.util.Arrays;
import java.util.List;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.AlleleValuesProvider;
import it.red.algen.dataprovider.InMemoryAlleleValuesProvider;
import it.red.algen.domain.genetics.genotype.Allele;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 * 
 * Simple structure for two math expressions like:
 * - 5 * 4
 * 0:signum 1:operand 2:operator 3:operand
 * or
 * cos(4.0 * 3.5)
 * 0:unaryoperator 1:signum 2:operand 3:operator 4:operand
 * 
 * 
 * Structure:
 * - two chromosome
 * - genes: 
 * 	+ signum ('+''-')
 *  + operand (int)
 *  + operator ('+','-','*','/')
 *  + unaryoperator ("sin","cos","tan")
 * 
 * 
 */
public class StandardMetadataGenomaTest 
    extends TestCase
{

	private StandardMetadataGenoma genoma;

	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StandardMetadataGenomaTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( StandardMetadataGenomaTest.class );
    }

    /**
     * Two chromosome of 5 and 4 positions each
     */
    public void setUp(){
    	GenesMetadataConfiguration genes = (GenesMetadataConfiguration)ReadConfigSupport.retrieveGenesMetadata("testapp");
    	
    	AlgorithmContext context = new AlgorithmContext();
    	context.application.alleleGenerator = new MetadataAlleleGenerator();
    	genoma = MetadataGenomaBuilder.create(context);
    	
    	List<Allele> unaryOperators = Arrays.asList(
    			new Allele<String>("sin"),
    			new Allele<String>("cos"),
    			new Allele<String>("tan"));
    	
    	List<Allele> binaryOperators = Arrays.asList(
    			new Allele<Character>('+'),
    			new Allele<Character>('-'),
    			new Allele<Character>('*'),
    			new Allele<Character>('/'));
    	
    	// Creates ValuesProvider
    	AlleleValuesProvider valuesProvider = new InMemoryAlleleValuesProvider();
    	valuesProvider.insertAlleles("unaryOperatorProvider", unaryOperators);
    	valuesProvider.insertAlleles("binaryOperatorProvider", binaryOperators);
    	
    	// Build Genoma
    	MetadataGenomaBuilder.setupAlleleValuesProvider(genoma, valuesProvider);
    	MetadataGenomaBuilder.addGenes(genoma, genes);
    	
    	context.application.alleleGenerator.setup(genoma);
		MetadataGenomaBuilder.finalize(genoma);
    }
    
    
    public void testPositions(){
    	assertEquals(9, genoma.getGenotypeStructure().getPositionsSize());
    	assertEquals(Arrays.asList(
    			"0.0","0.1","0.2","0.3",
    			"1.0","1.1","1.2","1.3","1.4"), 
    			genoma.getGenotypeStructure().getPositions());
    }

    
    public void testElementsCount(){
    	assertEquals(2, genoma.getGenotypeStructure().getNumberOfChromosomes());
    	assertEquals(4, genoma.getGenotypeStructure().getNumberOfGenes(0)); 
    	assertEquals(5, genoma.getGenotypeStructure().getNumberOfGenes(1)); 
    }
    
    public void testAlleleValuesProvider(){
    	List<Allele> alleles = genoma.getAlleles(genoma.getMetadataByCode("unaryOperator"));
    	assertFalse(alleles.isEmpty());
    	assertTrue(alleles.contains(new Allele("cos")));
    	assertTrue(alleles.contains(new Allele("sin")));
    	assertTrue(alleles.contains(new Allele("tan")));
    	
    	alleles = genoma.getAlleles(genoma.getMetadataByCode("binaryOperator"));
    	assertFalse(alleles.isEmpty());
    	assertTrue(alleles.contains(new Allele('+')));
    	assertTrue(alleles.contains(new Allele('-')));
    	assertTrue(alleles.contains(new Allele('*')));
    	assertTrue(alleles.contains(new Allele('/')));
    }
    	
    public void testAlleleValuesProviderRandom(){
    	List<String> unaryOps = Arrays.asList("cos","sin","tan");
    	assertTrue(unaryOps.contains(genoma.getRandomAllele("1.0").value));
    	assertTrue(unaryOps.contains(genoma.getOrderedAlleles().get(4).value));

    	List<Character> binaryOps = Arrays.asList('*','-','/','+');
    	assertTrue(binaryOps.contains(genoma.getRandomAllele("0.2").value));
    	assertTrue(binaryOps.contains(genoma.getRandomAllele("1.3").value));
    	assertTrue(binaryOps.contains(genoma.getOrderedAlleles().get(2).value));
    	assertTrue(binaryOps.contains(genoma.getOrderedAlleles().get(7).value));
    }
    
}
