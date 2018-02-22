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

import org.elastxy.core.domain.genetics.Genotype;
import org.elastxy.core.domain.genetics.phenotype.Phenotype;

/**
 *
 * @author grossi
 */
@SuppressWarnings("rawtypes")
public interface Solution<S extends Solution, F extends Fitness> {
	
	
	/**
	 * Hidden genetic information held by the solution
	 * 
	 * E.g. DNA sequence in the human individuals
	 * 
	 * @return
	 */
	public Genotype getGenotype();
	public void setGenotype(Genotype genotype);
	
	
	/**
	 * Visible manifestation of the hidden genotype
	 * 
	 * E.g. the whole individual with its exterior characteristics,
	 * such as green eyes or height
	 * 
	 * @return
	 */
	public Phenotype getPhenotype();
	public void setPhenotype(Phenotype phenotype);
	
	
	/**
	 * Performance indicator about how the individual performs in the environment,
	 * normalized to 1.
	 * 
	 * TODO3-8: study and apply normalization techniques
	 * 
	 * @return
	 */
    public F getFitness();
    
    public void setFitness(F fitness);
    

	/**
	 * Deep copy of all Solution attributes.
	 */
    public S copy();
    
	/**
	 * Deep copy of only the genetic material (fitness and phenotype must be recalculated).
	 * 
	 * Necessary when copying Solutions for recombination or mutation.
	 * 
	 * @return
	 */
    public S copyGenotype();
    
    
    /**
     * Short description
     * @return
     */
    public String toString();
        
    
//    /**
//     * Long description
//     * @return
//     */
//    public String toStringDetails();
}
