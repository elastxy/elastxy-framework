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
package org.elastxy.core.domain.genetics.genotype;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.engine.operators.MutatorLogics;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A sequence of Genes
 * 
 * Position: "<gene>"
 * E.g. "0", "1", .. , "N" 
 * 
 * @author red
 *
 */
public class Chromosome implements Genotype {
	public List<Gene> genes = new ArrayList<Gene>();

	
	/**
	 * Returns the list of available positions: [0..N]
	 */
//	@Cacheable(value = "genotype_positions") // TODO2-2: cache!
	@JsonIgnore
	@Override
	public List<String> getPositions() {
		List<String> result = IntStream.
				range(0, genes.size()).
				mapToObj(x -> String.valueOf(x)).
				collect(Collectors.toList());
		return result;
	}
	
	
	/**
	 * Replaces: mutate an allele with another in the same position
	 */
	@Override
	public void replaceAllele(String position, Allele allele) {
		genes.get(Integer.parseInt(position)).allele = allele;
	}
	

	/**
	 * Swaps: change position of a given allele with another at sibling position
	 */
	@Override
	public void swapAllele(String position, Allele newAllele) {
		MutatorLogics.swapAllele(genes, position, newAllele);
	}
	
	
	/**
	 * Encode the genotype in a single String representing the dominant alleles
	 * 
	 * Chromosomes are separated by a '.' character
	 * 
	 * E.g. [-132.+.87]
	 *  
	 * @return
	 */
	@Override
	public String encode(){
		return genes.stream().map(g -> g.encode()).collect(Collectors.joining());
	}
	
	
	@Override
	public Chromosome copy(){
		Chromosome result = new Chromosome();
		result.genes = genes.stream().map(g -> g.copy()).collect(Collectors.toList());
		return result;
	}
	
	
	public String toString(){
		return genes.toString();
	}
	

	
	public List<Allele> toAlleleList(){
		List<Allele> result = genes.stream().map(x -> x.allele).collect(Collectors.toList());
		return result;
	}
}
