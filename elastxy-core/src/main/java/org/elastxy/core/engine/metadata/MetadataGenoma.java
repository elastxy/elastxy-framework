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

import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;

/**
 * A Genoma based on Metadata: every Gene has properties for generating
 * new Alleles, within a Gene specific set, range or totally random based
 * on its characteristics.
 * 
 * Position codification depends entirely on genotype type.
 * 
 * E.g. when multiple chromosomes are involved, positions are in the form of "x.y" instead of "x".
 * See also: {@link SequenceGenotype}, {@link ChromosomeGenotype}, {@link DoubleStrandGenotype}
 * 
 * TODO3-2: evaluate if it's useful to create a specific MetadataGenoma type "ChromosomeMetadataGenoma"
 * composing multiple "StandardMetadataGenoma", one for each chromosome...
 * 
 * @author red
 *
 */
public interface MetadataGenoma extends Genoma {

	
	/**
	 * Inject an allele generator implementation.
	 * @param generator
	 */
	public void setupAlleleGenerator(AlleleGenerator generator);
	

	/**
	 * Get the metadata by code
	 * TODO2-2: move getMetadataByCode to Structure?
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByCode(String metadataCode);
	

	/**
	 * Get the metadata by a given position
	 * TODO2-2: move getMetadataByPosition to Structure?
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByPosition(String position);
	
	
	
	

//	/**
//	 * Generates one Allele for every possible values of the metadata.
//	 * 
//	 * @return
//	 */
//	public List<Allele> getRandomAllelesByCode(String metadataCode);
	
	

	/**
	 * Retrieves a random Allele suitable for the given metadata.
	 * 
	 * TODO3-2: only by metadatacode String?
	 * TODO1-8: reorder and document these APIs
	 * TODO2-4: create a JavaDoc on important APIs!
	 * 
	 * @param metadata
	 * @return
	 */
	public Allele getRandomAllele(GeneMetadata metadata);

	
	/**
	 * Retrieves the list of Alleles suitable for the given metadata.
	 * 
	 * TODO2-2: only by metadatacode String?
	 * 
	 * @param metadata
	 * @return
	 */
	public List<Allele> getAlleles(GeneMetadata metadata);
	
	
//	/**
//	 * Generate new Allele list based on given metadata.
//	 * 
//	 * It cannot be performed if allele are limited, because it's not position based
//	 * and can be arbitrary called N times.
//	 * 
//	 * @param metadataCode
//	 * @return
//	 */
//	public List<Allele> createRandomAllelesByCodes(List<String> metadataCodes);

//	/**
//	 * Generates a new Allele based on specific value.
//	 * 
//	 * It cannot be performed if allele are limited, because it can be arbitrary called.
//	 * 
//	 * An exception is raise if value is not present between metadata available values.
//	 */
//	public Allele createAlleleByValue(String metadataCode, Object value);
	
	
}
