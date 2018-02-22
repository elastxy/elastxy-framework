/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.elastxy.core.domain.genetics;

import org.elastxy.core.engine.genetics.GenomaPositionComparator;

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
