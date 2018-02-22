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
package org.elastxy.core.domain.experiment;

import java.io.Serializable;

import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.phenotype.Phenotype;

public class GenericSolution implements Solution, Serializable {
	public Genotype genotype;
	public Phenotype phenotype;
	public Fitness fitness;

	@Override
	public Genotype getGenotype() {
		return genotype;
	}

	@Override
	public void setGenotype(Genotype genotype) {
		this.genotype = genotype;
	}

	@Override
	public Phenotype getPhenotype() {
		return phenotype;
	}

	@Override
	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
	}

	@Override
	public Fitness getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(Fitness fitness) {
		this.fitness = fitness;
	}

	
	@Override
	public Solution copy() {
		GenericSolution result = new GenericSolution();
		result.setFitness(fitness!=null?fitness.copy():null);
		result.genotype = (Genotype)genotype!=null?genotype.copy():null;
		result.phenotype = (Phenotype)phenotype!=null?phenotype.copy():null;
		return result;
	}

	@Override
	public Solution copyGenotype() {
		GenericSolution result = new GenericSolution();
		result.genotype = (Genotype)genotype!=null?genotype.copy():null;
		return result;
	}

	@Override
	public String toString() {
		return String.format("SOL:Ge[%s] > Ph[%s] > F[%s]", genotype, phenotype, fitness);
	}
	
//	@Override
//	public String toStringDetails() {
//		return String.format("[Sol: %s; Ph: %s; Fit: %s", toString(), phenotype, fitness!=null?fitness.toString():null);
//	}
	
}
