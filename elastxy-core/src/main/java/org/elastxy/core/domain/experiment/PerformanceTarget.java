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
import java.math.BigDecimal;

import org.elastxy.core.engine.core.MathUtils;
import org.elastxy.core.engine.fitness.FitnessUtils;

/**
 * A generic target class expressing a generic performance index as a goal.
 * 
 * E.g. goal is the number to calculate, whereas measure is a measure of the distance 
 * to the interval upper limit
 * 
 * @author red
 * @param <G>
 *
 */
public class PerformanceTarget<G,M> implements Target<G,M>, Serializable {
	public G goal;
	public M referenceMeasure;
	
	public BigDecimal targetFitness;
	public BigDecimal targetThreshold;

	
	@Override
	public G getGoal() {
		return goal;
	}
	@Override
	public void setGoal(G goal) {
		this.goal = goal;
	}

	
	@Override
	public M getReferenceMeasure() {
		return referenceMeasure;
	}
	@Override
	public void setReferenceMeasure(M measure) {
		this.referenceMeasure = measure;
	}
	


	@Override
	public BigDecimal getTargetFitness() {
		return targetFitness;
	}

	/**
	 * Sets target fitness, values in interval [0.0;1.0].
	 * 
	 * If value is near 1.0 or 0.0 at a big scale, is approximated.
	 * 
	 */
	@Override
	public void setTargetFitness(BigDecimal fitness) {
		this.targetFitness = checkUndefined(fitness) ? null : FitnessUtils.approximateFitness(fitness);
	}
	
	
	@Override
	public BigDecimal getTargetThreshold() {
		return targetThreshold;
	}
	
	@Override
	public void setTargetThreshold(BigDecimal threshold) {
		this.targetThreshold = checkUndefined(threshold) ? null : FitnessUtils.approximateFitness(threshold);
	}
	
	private boolean checkUndefined(BigDecimal parameter){
		if(parameter==null || MathUtils.equals(parameter, BigDecimal.valueOf(-1.0))){
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public String toString(){
		return String.format("PerformanceTarget[goal=%s;targetFitness=%.3f,threshold=%.3f", goal==null?null:goal.toString(), targetFitness, targetThreshold);
	}
}
