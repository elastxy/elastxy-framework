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
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.domain.genetics.genotype.Gene;

public class MutatorLogics {

	
	/**
	 * Swap positions of two alleles
	 * @param genes
	 * @param position
	 * @param newAllele
	 */
	public static void swapAllele(List<Gene> genes, String position, Allele newAllele) {
		
		// If the value is the same, leave it
		int newPosition = Integer.parseInt(position);
		if(genes.get(newPosition).allele.equals(newAllele)){
			return;
		}
		
		// Search for old position of the newAllele.. 
		OptionalInt oldPosition = IntStream.range(0, genes.size())
			     .filter(i -> newAllele.equals(genes.get(i).allele))
			     .findFirst();
		
		// New position is occupied by another allele..
		Allele otherAllele = genes.get(newPosition).allele;
		
		// That allele will replace new at its old position
		genes.get(oldPosition.getAsInt()).allele = otherAllele;
		genes.get(newPosition).allele = newAllele;
	}
	
	
}
