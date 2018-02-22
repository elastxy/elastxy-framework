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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.engine.operators.MutatorLogics;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Chromosome grouped lists of genes
 * 
 * Position: "<chromosome>.<gene>"
 * E.g. "0.0", "0.1", .. , "5.4", .. , "M.N" 
 * 
 * TODO3-4: rework to a GenePosition to represent position
 *
 * @author red
 *
 */
public class Strand implements Genotype {
	public List<Chromosome> chromosomes = new ArrayList<Chromosome>();

	/**
	 * For every gene, encode the gene value
	 * 
	 * TODO3-4: find a usage of encoded Genotype :)
	 */
	@Override
	public String encode() {
		String result = chromosomes.stream().map(c -> c.encode()).collect(Collectors.joining());
		return result;
	}
	
	
	@Override
	public Strand copy() {
		Strand result = new Strand();
		result.chromosomes = chromosomes.stream().map(c -> c.copy()).collect(Collectors.toList());
		return result;
	}


	@JsonIgnore
	@Override
	public List<String> getPositions() {
		List<String> result = new ArrayList<String>();
		int tot = chromosomes.size();
		for(int c=0; c < tot; c++){
			final int ch = c;
			result.addAll(IntStream.
				range(0, chromosomes.get(c).genes.size()).
				mapToObj(x -> ch+"."+String.valueOf(x)).
				collect(Collectors.toList()));
		}
		return result;
	}

	@JsonIgnore
	public int getNumberOfChromosomes() {
		return chromosomes.size();
	}

	@JsonIgnore
	public List<String> getPositions(int chromosome) {
		List<String> result = new ArrayList<String>();
		result.addAll(IntStream.
			range(0, chromosomes.get(chromosome).genes.size()).
			mapToObj(x -> chromosome+"."+String.valueOf(x)).
			collect(Collectors.toList()));
		return result;
	}

	@Override
	public void replaceAllele(String position, Allele allele) {
		String[] splitted = position.split("\\.");
		Chromosome chromosome = chromosomes.get(new Integer(splitted[0]));
		chromosome.genes.get(new Integer(splitted[1])).allele = allele;
	}

	@Override
	public void swapAllele(String position, Allele newAllele) {
		String[] splitted = position.split("\\.");
		Chromosome chromosome = chromosomes.get(new Integer(splitted[0]));
		MutatorLogics.swapAllele(chromosome.genes, position, newAllele);
	}
	
	public void assignAlleles(SortedMap<String,Allele> alleles){
		Iterator<Map.Entry<String,Allele>> it = alleles.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,Allele> entry = it.next();
			String[] splitted = entry.getKey().split("\\.");
			int chromosome = new Integer(splitted[0]);
			int gene = new Integer(splitted[1]);
			this.chromosomes.get(chromosome).genes.get(gene).allele = entry.getValue();
		}
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append("Chromosomes: ");
		for(Chromosome c : chromosomes){
			result.append(c);
		}
		return result.toString();
	}
}
