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
package org.elastxy.core.engine.operators;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.engine.factory.PopulationFactory;

public class UniformlyDistributedSelector implements Selector<Genoma> {
	private AlgorithmContext context;
	
	public void setup(AlgorithmContext context){
		this.context = context;
    }

    /** SELECTION
     *  Creates a new random population
     */
    public Population select(Population actualGeneration, Genoma genoma){
    	PopulationFactory populationFactory = context.application.populationFactory;
    	Population nextGen = populationFactory.createNew(
    			genoma, 
    			context.algorithmParameters.initialSelectionNumber, 
    			context.algorithmParameters.initialSelectionRandom);
        return nextGen;
    }
	    
}
