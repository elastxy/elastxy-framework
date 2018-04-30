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
package ${groupId}.singlecolony;

import java.math.BigDecimal;

import org.elastxy.core.applications.ApplicationException;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.NumberRawFitness;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.engine.factory.TargetBuilder;

public class AppTargetBuilder implements TargetBuilder<PerformanceTarget, BigDecimal>{
	private AlgorithmContext context;
	
	@Override
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	public Target<PerformanceTarget, BigDecimal> define(WorkingDataset workingDataset) {
		
		// Defines goal representation
        Long targetValue = context.applicationSpecifics.getTargetLong(AppConstants.TARGET_EXPRESSION_RESULT);
        PerformanceTarget target = new PerformanceTarget();
        target.setGoal(targetValue);

        // Determines goal rough measure by deriving from extreme solutions
        long maxOperandValue = context.applicationSpecifics.getParamLong(AppConstants.MAX_OPERAND_VALUE);
        NumberRawFitness raw = new NumberRawFitness(
        		new BigDecimal(Math.max((maxOperandValue*maxOperandValue)+targetValue, (maxOperandValue*maxOperandValue)-targetValue)));
        if(raw.value.doubleValue() < 0){
        	throw new ApplicationException("Negative distance not allowed: check numbers precision.");
        }
        target.setReferenceMeasure(raw.value);
		return target;
	}
}
