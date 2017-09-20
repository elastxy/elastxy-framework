package it.red.algen.conf;

import java.io.IOException;
import java.util.Arrays;

import it.red.algen.metadata.GeneMetadataType;
import it.red.algen.metadata.GenesMetadataConfiguration;
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
    	assertEquals(2, genes.metadata.size());
    	assertEquals(GeneMetadataType.CHAR, genes.metadata.get("operator").type);
    	assertEquals(4, genes.metadata.get("operator").values.size());
    	assertEquals(Arrays.asList('+','-','*','/'), genes.metadata.get("operator").values);

    	// Positions
    	assertEquals(2, genes.positions.size());
    	assertEquals(2, genes.positions.get("operand").size());
    }
    
}