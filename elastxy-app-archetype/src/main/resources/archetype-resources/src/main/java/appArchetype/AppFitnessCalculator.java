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
package ${groupId}.appArchetype;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.NumberRawFitness;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.RawFitness;
import org.elastxy.core.domain.experiment.StandardFitness;
import org.elastxy.core.domain.genetics.phenotype.NumberPhenotype;
import org.elastxy.core.engine.fitness.AbstractFitnessCalculator;

public class AppFitnessCalculator extends  AbstractFitnessCalculator<GenericSolution,StandardFitness> {
	private static final Logger logger = Logger.getLogger(AppFitnessCalculator.class);

	
	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * 
	 * @return
	 */
	@Override
	protected final RawFitness calculateRaw(GenericSolution solution, Env env){
        PerformanceTarget<Long,BigDecimal> target = (PerformanceTarget<Long,BigDecimal>)env.target;
    	long sValue = ((NumberPhenotype)solution.phenotype).getValue().longValue();
        
    	// Calculate distance from goal
    	long tValue = (long)target.getGoal();
    	long distance = Math.abs(tValue-sValue);
    	return new NumberRawFitness(distance);
	}
		
	@Override
	protected final BigDecimal normalize(GenericSolution solution, Env env, RawFitness rawFitness){
		Double distance = ((NumberRawFitness)rawFitness).value.doubleValue();
        PerformanceTarget<Long,BigDecimal> target = (PerformanceTarget<Long,BigDecimal>)env.target;
        return BigDecimal.ONE.subtract(new BigDecimal(distance).setScale(20).divide(target.getReferenceMeasure().setScale(20), BigDecimal.ROUND_HALF_UP));
	}
	
	
}
