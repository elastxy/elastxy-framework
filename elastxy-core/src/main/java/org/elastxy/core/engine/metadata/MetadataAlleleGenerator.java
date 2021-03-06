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
package org.elastxy.core.engine.metadata;

import java.util.List;
import java.util.stream.Collectors;

import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.engine.core.Randomizer;


/**
 * Generate Alleles exclusively based on metadata.
 * @author red
 *
 */
public class MetadataAlleleGenerator implements AlleleGenerator {

	protected MetadataGenoma genoma;

	
	/**
	 * Genoma can provider Allele values based from Metadata.
	 */
	@Override
	public void setup(Genoma genoma) {
		this.genoma = (MetadataGenoma)genoma;
	}
	
	
	/**
	 * Generates a new instance provided with only the given value.
	 */
	@Override
	public <T> Allele<T> generateFromValue(T value) {
		Allele<T> result = new Allele<T>();
		result.value = (T)value;
		return result;
	}


	/**
	 * Generate a new instance from a random value from metadata.
	 */
	@Override
	public <T> Allele<T> generateRandom(GeneMetadata metadata) {
		Allele<T> result = null;
		if(metadata.valuesProvider!=null){
//			List<Allele> alleles = genoma.getAlleles(metadata.valuesProvider);
//			result = alleles.get(Randomizer.nextInt(alleles.size()));
			result = genoma.getRandomAllele(metadata);
		}
		else {
			result = new Allele<T>();
			result.value = (T)GeneMetadataPicker.randomPick(metadata);
		}
		return result;
	}
	

	/**
	 * Generates a new instance with a random value from metadata remaining 
	 * after exclusions.
	 */
	@Override
	public <T> Allele<T> generateExclusive(GeneMetadata metadata, List<T> exclusions) {
		Allele<T> result = null;
		if(metadata.valuesProvider!=null){
			List<Allele> alleles = genoma.getAlleles(metadata);
			List<Allele> subtracted = alleles.stream().filter(a -> !exclusions.contains(a.value)).collect(Collectors.toList());
			result = subtracted.get(Randomizer.nextInt(subtracted.size()));
		}
		else {
			result = new Allele<T>();
			List<T> subtracted = (List<T>)metadata.values.stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
			if(subtracted.isEmpty()){
				throw new AlgorithmException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
			}
			result.value = (T)subtracted.get(Randomizer.nextInt(subtracted.size()));
		}
		return result;
	}

	
	/**
	 * Generates an instance with always the same value (first from metadata).
	 * Useful when requesting always the same Allele from a given metadata
	 * (e.g. when creating a new population with same genetic material).
	 */
	@Override
	public <T> Allele<T> generateFirst(GeneMetadata metadata) {
		Allele<T> result = null;
		if(metadata.valuesProvider!=null){
			List<Allele> alleles = genoma.getAlleles(metadata);
			result = alleles.get(0);
		}
		else {
			result = new Allele<T>();
			result.value = (T)GeneMetadataPicker.pickFirst(metadata);
		}
		return result;
	}


}
