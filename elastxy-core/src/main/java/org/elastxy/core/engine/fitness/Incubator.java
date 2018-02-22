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
package org.elastxy.core.engine.fitness;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.phenotype.Phenotype;
import org.elastxy.core.engine.core.IllegalSolutionException;

public interface Incubator<G extends Genotype, P extends Phenotype> {

	/**
	 * Create the phenotype starting from the genotype.
	 * 
	 *  If needed, the surrounding environment can be provided to give Incubator
	 *  further data for growing individuals.
	 *  
	 *  E.g. in Mef WorkingDataset is given to get meaningful data from original recipes.
	 *  
	 *  TODO2-4: EnvironmentAware interface for injecting Env runtime context without passing in signatures
	 * @param genotype
	 * @return
	 */
	public P grow(G genotype, Env environment) throws IllegalSolutionException;
	
}
