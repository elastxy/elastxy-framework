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
 * Base implementation of GenotypeStructure.
 * 
 * @author red
 *
 */
public abstract class GenotypeStructureImpl implements GenotypeStructure {

	/**
	 * Number of positions.
	 */
	protected int positionsSize;
	
	/**
	 * Flat ordered positions.
	 */
	protected List<String> positions;

	/**
	 * Number of chromosomes: if 1 is a single chromosome, more is a Strand.
	 */
	protected int numberOfChromosomes;
	
	
	@Override
	public int getPositionsSize(){
		return positionsSize;
	}


	@Override
	public List<String> getPositions() {
		return positions;
	}


	@Override
	public int getNumberOfChromosomes() {
		return numberOfChromosomes;
	}


	@Override
	public abstract int getNumberOfGenes(int chromosome);

	
// TODO3-4: multistrands
//	@Override
//	public int getNumberOfStrands() {
//		return 0;
//	}
}
