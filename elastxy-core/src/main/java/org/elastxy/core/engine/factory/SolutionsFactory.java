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

import org.elastxy.core.domain.experiment.Solution;

public interface SolutionsFactory<G> {

	/**
	 * Create a random Solution with random Alleles from Genoma.
	 * 
	 * E.g. '4 * 7'
	 * 
	 * Used in random option in PopulationFactory.
	 * 
	 * @return
	 */
	public Solution createRandom(G genoma);
	

	/**
	 * Create a default, always the same initial Solution (to be evolved by mutation).
	 * 
	 * E.g. '0 + 0'
	 * 
	 * Used in non-random option in PopulationFactory
	 * 
	 * @return
	 */
	public Solution createBaseModel(G genoma);
	
	

//	/**
//	 * Create a solution populated with predefined values.
//	 * 
//	 * Useful to create benchmark solutions, or as help to calculate target, 
//	 * boundaries, etc.
//	 * 
//	 * E.g. '-1000 * 1000'
//	 * 
//	 * @return
//	 */
//	public Solution createPredefined(G genoma, List<Object> alleleValues);
}
