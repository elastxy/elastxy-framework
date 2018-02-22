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
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.phenotype.Phenotype;

/**
 * Calculate the fitness value of a Solution phenotype or genotype 
 * with respect to an Environment
 * 
 * @author red
 *
 * @param <S>
 * @param <T>
 * @param <F>
 */
public interface FitnessCalculator<S extends Solution, F extends Fitness> {

	/** Assign an incubator for growing and evaluating the offsprings
	 * 
	 * @param incubator
	 */
	public void setIncubator(Incubator<? extends Genotype, ? extends Phenotype> incubator);
	

	/**
	 * Calculate Fitness value of a Solution given an Environment (Target and sorroundings conditions)
	 * 
	 * If needed, the FitnessCalculator can be aware of the surrounding environment
	 * for the solution to grow.
	 * 
	 * @param solution
	 * @param target
	 * @return
	 */
    public F calculate(S solution, Env environment);
    
}
