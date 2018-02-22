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

import java.util.List;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Strand;

/**
 * Standard mutator based on metadata
 * @author red
 *
 */
public class StrandMutator implements Mutator<Solution, Genoma> {
	

	@Override
	public Solution mutate(Solution solution, Genoma genoma) {
		
		// Reset fitness so that it must be recalculated
		solution.setFitness(null);
		
		// Replace an allele to another for the same position for each chromosome
		Strand genotype = (Strand)solution.getGenotype();
		int tot = genoma.getGenotypeStructure().getNumberOfChromosomes();
		for(int c=0; c < tot; c++){
			List<String> positions = genotype.getPositions(c);
			genoma.mutate(solution, positions);
		}
		
		return solution;
	}

}
