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
package org.elastxy.core.engine.factory;

import java.util.List;
import java.util.stream.IntStream;

import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.engine.genetics.PredefinedGenoma;
import org.elastxy.core.engine.genetics.PredefinedGenotypeFactory;

public class PredefinedGenomaSolutionsFactory  implements SolutionsFactory<PredefinedGenoma>{

	@Override
    public Solution createRandom(PredefinedGenoma genoma) {
    	return createSolution(genoma, genoma.getRandomAlleles());
    }
    
	@Override
    public Solution createBaseModel(PredefinedGenoma genoma) {
    	return createSolution(genoma, genoma.getOrderedAlleles());
    }

	private Solution createSolution(PredefinedGenoma genoma, List<Allele> alleles) {
		GenericSolution solution = new GenericSolution();
    	solution.genotype = PredefinedGenotypeFactory.createGenotype(genoma);

    	// Assign to every Gene an Allele
    	IntStream.range(0, ((Chromosome)solution.genotype).genes.size()).
    		forEach(i -> ((Chromosome)solution.genotype).genes.get(i).allele = alleles.get(i));
    	
    	return solution;
	}

//	@Override
//	public Solution createPredefined(PredefinedGenoma genoma, List<Object> alleleValues) {
//		GenericSolution solution = new GenericSolution();
//    	solution.genotype = PredefinedGenotypeFactory.createGenotype(genoma);
//
//    	IntStream.range(0, ((Chromosome)solution.genotype).genes.size()).
//    		forEach(i -> ((Chromosome)solution.genotype).genes.get(i).allele = new Allele(alleleValues.get(i)));
//    	
//    	return solution;
//	}

	
}
