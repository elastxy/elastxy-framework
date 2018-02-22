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

import java.util.Arrays;
import java.util.List;

import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.engine.core.Randomizer;

public class ChromosomeRecombinator implements Recombinator<Solution> {
    private AlgorithmParameters algorithmParameters;
    
    
    public void setup(AlgorithmParameters parameters) {
    	this.algorithmParameters = parameters;
    }

    
    
    /**
     * Recombination of two Solution. Two are the expected parents.
     * 
     * TODO1-2: check performance of Recombination operator
     * TODO3-4: more than 2 parents management
     * 
     * @param other
     * @return
     */
	public List<Solution> recombine(List<Solution> parents, boolean preserveAlleles){
		
		// Define cut point
		// TODO2-1: define cut point from metadata!
		Chromosome genotype0 = (Chromosome)parents.get(0).getGenotype();
		
		int genesSize = genotype0.genes.size();
		Solution[] offsprings = RecombinatorLogics.generateOffsprings(parents, genesSize, null, preserveAlleles, algorithmParameters.crossoverPointRandom);
		
		// Returns the array
        return Arrays.asList(offsprings);
    }
	
}
