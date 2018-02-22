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
package org.elastxy.core.dataprovider;

import java.util.List;

import org.elastxy.core.domain.genetics.genotype.Allele;


/**
 * Returns list of possible values for an Allele.
 * 
 * To be used when list is long and should not be assigned 
 * to every Gene.
 * 
 * The list can be:
 * - not related to any specific metadata
 * - related to one specific metadata
 * - shared between a number of metadata
 * 
 * The link between provider and metadata resides in valuesProvider 
 * property of GeneMetadata for MetadataGenoma.
 * 
 * An implementing class can provide all or none of the methods,
 * depending on the usage context.
 * 
 * @author red
 *
 */
public interface AlleleValuesProvider {
	
	/**
	 * Returns the number of providers
	 * @return
	 */
	public int countProviders();
	
	/**
	 * Return the list of possible alleles.
	 * @return
	 */
	public List<Allele> getAlleles();
	
	
	/**
	 * Set the list of possible alleles.
	 * @param alleles
	 */
	public void insertAlleles(List<Allele> alleles);
	
	
	/**
	 * Returns the list of all possible alleles
	 * for a specific provider.
	 * 
	 * @param provider - if null alleles are not related to a specific provider
	 * @return
	 */
	public List<Allele> getAlleles(String provider);
	
	
	/**
	 * Add a list of Allele to a specific provider.
	 * 
	 * @param provider - null if list is not related to any metadata
	 * @param alleles
	 */
	public void insertAlleles(String provider, List<Allele> alleles);
	
	
}
