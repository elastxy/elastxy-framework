package it.red.algen.conf;

import java.io.IOException;
import java.util.Arrays;

import it.red.algen.engine.metadata.GeneMetadataType;
import it.red.algen.engine.metadata.GenesMetadataConfiguration;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ReadConfigTest 
    extends TestCase
{
	

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ReadConfigTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ReadConfigTest.class );
    }

    
    public void testMetadata() throws IOException{
    	GenesMetadataConfiguration genes = (GenesMetadataConfiguration)ReadConfigSupport.retrieveGenesMetadata("testapp");
    	
    	// Metadata
    	assertEquals(3, genes.metadata.size());
    	assertEquals(GeneMetadataType.CHAR, genes.metadata.get("operator").type);
    	assertEquals(4, genes.metadata.get("operator").values.size());
    	assertEquals(Arrays.asList('+','-','*','/'), genes.metadata.get("operator").values);

    	// Positions
    	assertEquals(3, genes.positions.size());
    	assertEquals(2, genes.positions.get("operandInt").size());
    	assertEquals(2, genes.positions.get("operator").size());
    	assertEquals(1, genes.positions.get("operandValues").size());
    }
    
}