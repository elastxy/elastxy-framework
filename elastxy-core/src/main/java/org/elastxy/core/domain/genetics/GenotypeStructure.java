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

import java.util.List;

/**
 * Represents the structure of a Genotype produced by the Genoma.
 * 
 * It's initialized after creating a Genoma and does not change anymore.
 * It can be used as a blueprint for creating Genotypes by Genoma.
 *  
 * @author red
 *
 */
public interface GenotypeStructure {

	/**
	 * Returns the size of all available positions:
	 * - chromosome: number of genes
	 * - strand: sum of the number of genes of all chromosomes
	 * @return
	 */
	public int getPositionsSize();
	
	/**
	 * Returns ordered positions codes:
	 * - progressive int "X" for a sequence
	 * - progressive couple of int "X.Y" for a single strand
	 * - progressive triple of int "X.Y.Z" for a double strand
	 * @return
	 */
	public List<String> getPositions();
	
//	/**
//	 * Returns the number of chromosomes by strand
//	 * TODO3-4: multistrands
//	 * @return
//	 */
//	public int getNumberOfStrands();

	/**
	 * Returns the number of chromosomes by strand
	 * @return
	 */
	public int getNumberOfChromosomes();

	/**
	 * Returns the number of genes for given chromosome
	 * TODO3-4: multistrands
	 * @return
	 */
	public int getNumberOfGenes(int chromosome);
	
}
