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
package org.elastxy.core.engine.operators;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;


/**
 * Mutator operator.
 *
 * TODO1-2: check performance of Mutator operator implementations.
 * 
 * @author red
 */
@SuppressWarnings("rawtypes")
public interface Mutator<S extends Solution, G extends Genoma> {
	
	/**
	 * Takes a solution as an input and applies mutator operator,
	 * returning the original solution mutate
	 * 
	 * @param solution
	 * @return
	 */
	public S mutate(S solution, G genoma);
}
