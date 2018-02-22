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

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.elastxy.core.engine.core.AlgorithmException;

/**
 * A single chromosomes genotype structure.
 * @author red
 *
 */
public class ChromosomeGenotypeStructure extends GenotypeStructureImpl {

	/**
	 * Initialize structure by number of positions.
	 * 
	 * @param numberOfPositions
	 */
	public void build(int numberOfPositions){
		positionsSize = numberOfPositions;
		positions = IntStream.range(0, numberOfPositions).boxed().map(i -> i.toString()).collect(Collectors.toList());
		numberOfChromosomes = 1;
	}
	
	@Override
	public int getNumberOfGenes(int chromosome){
		if(chromosome!=0){
			throw new AlgorithmException("Chromosome cannot be more than 0 for a single chromosome structure. Chromosome requested: "+chromosome);
		}
		return positionsSize;
	}
	
}
