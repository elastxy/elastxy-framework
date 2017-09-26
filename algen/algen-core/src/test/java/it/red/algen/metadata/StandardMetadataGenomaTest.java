package it.red.algen.metadata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import it.red.algen.engine.metadata.GeneMetadata;
import it.red.algen.engine.metadata.MetadataGenoma;
import it.red.algen.engine.metadata.MetadataGenomaBuilder;
import it.red.algen.engine.metadata.StandardMetadataGenoma;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
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
    	genoma = MetadataGenomaBuilder.create(null);
		for(int c=1; c >= 0; c--){
			for(int g=4; g >= 0; g--){
				if(c==1 && g==4) continue; // skip 5th gene of 2nd chromosome
				String pos = c+"."+g;
				GeneMetadata metadata = new GeneMetadata();
				metadata.code = "m"+pos;
				MetadataGenomaBuilder.addGene(genoma, pos, metadata);
			}
		}
		MetadataGenomaBuilder.finalize(genoma);
    }
    
    
    public void testPositions(){
    	assertEquals(9, genoma.getGenotypeStructure().getPositionsSize());
    	assertEquals(Arrays.asList(
    			"0.0","0.1","0.2","0.3","0.4",
    			"1.0","1.1","1.2","1.3"), 
    			genoma.getGenotypeStructure().getPositions());
    }

    
    public void testElementsCount(){
    	assertEquals(2, genoma.getGenotypeStructure().getNumberOfChromosomes());
    	assertEquals(5, genoma.getGenotypeStructure().getNumberOfGenes(0)); 
    	assertEquals(4, genoma.getGenotypeStructure().getNumberOfGenes(1)); 
    }
    
}
