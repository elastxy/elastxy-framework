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

import java.util.List;

import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.domain.experiment.Solution;


/**
 * Recombinator operator
 * 
 * @author red
 * @param <R>
 */
@SuppressWarnings("rawtypes")
public interface Recombinator<R extends Solution> {

    public void setup(AlgorithmParameters algParameters);
    
    
    /**
     * Returns a list of offspring generated from a list of parents,
     * cross-cutting the genotype as configured by operator parameters.
     * 
     * If preserveAlleles is set, recombination preserves the (limited) set
     * of genes of both solutions.
     * 
     * @param parents
     * @return
     */
	public List<R> recombine(List<R> parents, boolean preserveAlleles);
	
}
