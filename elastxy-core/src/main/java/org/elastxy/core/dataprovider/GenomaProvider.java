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

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;

/**
 * Collects all genoma information from a given source.
 * 
 * Optionally, Genoma view can be restricted to adhere to 
 * execution goal, for reducing genotype size and better efficiency.
 * 
 * Context: APPLICATION
 * 
 * @author red
 *
 */
public interface GenomaProvider {
	
	/**
	 * Setup runtime context for this component
	 * TODO3-4: ContextAware interface
	 * @return
	 */
	public void setup(AlgorithmContext context);
	
	/**
	 * Assign a working data set for accessing data, if needed.
	 * @param workingDataset
	 */
	public void setWorkingDataset(WorkingDataset workingDataset);
	
	/**
	 * Returns a reference to the Genoma previously generated.
	 * @return
	 */
	public Genoma getGenoma();
	
	/**
	 * Builds or retrieve the whole Genoma, caching if necessary 
	 * for following executions.
	 * @return
	 */
	public void collect();
	
	/**
	 * Reduce to the minimum set of information for building solutions
	 * during current execution.
	 * 
	 * Returns a new Genoma for current execution.
	 * 
	 * @param target
	 */
	public Genoma shrink(Target<?,?> target);
	
}
