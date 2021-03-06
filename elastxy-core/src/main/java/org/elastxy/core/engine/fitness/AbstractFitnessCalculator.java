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

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.RawFitness;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.StandardFitness;
import org.elastxy.core.engine.core.IllegalSolutionException;

public abstract class AbstractFitnessCalculator<S extends GenericSolution, F extends Fitness> implements FitnessCalculator {
	private static final Logger logger = Logger.getLogger(AbstractFitnessCalculator.class);

	
	private Incubator incubator;


	@Override
	public void setIncubator(Incubator incubator) {
		this.incubator = incubator;
	}
	
	
	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * 
	 * @return
	 */
	@Override
    public Fitness calculate(Solution sol, Env env) {
        GenericSolution solution = (GenericSolution)sol; // TODO1-2: generics: manage Solution generic type...
        
		// Setup fitness
		StandardFitness result = new StandardFitness();
        solution.setFitness(result);

        // Solution growth
        boolean growthCompleted = false;
        String legalCheck = null;
        RawFitness rawFitness = null;
        BigDecimal normalizedFitness = BigDecimal.ZERO;
        try { 
        	// Grow the offspring to evaluate it
        	solution.phenotype = incubator.grow(solution.genotype, env);
        	growthCompleted = true;
        } catch(IllegalSolutionException ex){
        	legalCheck = ex.getLegalCheck();
        	if(logger.isDebugEnabled()) logger.debug("Problem encountered while growing solution. Legal check: "+legalCheck);
        }
    	catch(Exception ex){
    		String msg = String.format("Generic error while growing solution genotype %s. Ex: %s", solution.genotype.toString(), ex.toString());
    		logger.error(msg, ex);
    	}

        if(growthCompleted) {
        	rawFitness = calculateRaw(solution, env);
        	normalizedFitness = normalize(solution, env, rawFitness);
        }
        
        // Create fitness result
        result.setValue(normalizedFitness);
        result.setRawValue(rawFitness);
        result.setLegalCheck(legalCheck);
        return result;
    }
	
	protected abstract RawFitness calculateRaw(GenericSolution solution, Env env);
	
	protected abstract BigDecimal normalize(GenericSolution solution, Env env, RawFitness rawFitness);


}
