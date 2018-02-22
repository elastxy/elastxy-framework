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

import java.io.Serializable;
import java.util.List;

import org.elastxy.core.domain.genetics.genotype.Allele;


/**
 * Represents the genetic material of the individual solution.
 * @author red
 *
 */
public interface Genotype extends Serializable {
	
	
	/**
	 * Returns the list of all available positions
	 * @return
	 */
	public List<String> getPositions();

	
	/**
	 * Replaces: mutate an allele with another in the same position
	 */
	public void replaceAllele(String position, Allele allele);
	

	/**
	 * Swaps: change position of a given allele with another at sibling position
	 */
	public void swapAllele(String position, Allele allele);

	
	/**
	 * Encode the genotype in a single String representing the dominant alleles
	 * 
	 * Chromosomes are separated by a '.' character
	 * 
	 * E.g. [-132.+.87]
	 *  
	 * @return
	 */
	public String encode();
	
	
	public Genotype copy();
}
