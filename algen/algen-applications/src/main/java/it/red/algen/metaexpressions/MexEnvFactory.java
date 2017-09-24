/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.metaexpressions;

import java.math.BigDecimal;

import it.red.algen.applications.ApplicationException;
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.dataaccess.WorkingDataset;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.engine.metadata.StandardMetadataGenoma;

/**
 * @author grossi
 */
public class MexEnvFactory extends AbstractEnvFactory<PerformanceTarget, BigDecimal, StandardMetadataGenoma> {
	
	// TODOM: take outside Target definition code, as a new Component
	@Override
	protected Target<PerformanceTarget, BigDecimal> defineTarget(WorkingDataset dataset) {
        
		// Defines goal representation
        Long targetValue = context.applicationSpecifics.getTargetLong(MexConstants.TARGET_EXPRESSION_RESULT);
        PerformanceTarget target = new PerformanceTarget();
        target.setGoal(targetValue);

        // Determines goal rough measure by deriving from extreme solutions
        // TODOM concept of boundaries
        long maxOperandValue = context.applicationSpecifics.getParamLong(MexConstants.MAX_OPERAND_VALUE);
        NumberRawFitness raw = new NumberRawFitness(
        		new BigDecimal(Math.max((maxOperandValue*maxOperandValue)+targetValue, (maxOperandValue*maxOperandValue)-targetValue)));
        if(raw.value.doubleValue() < 0){
        	throw new ApplicationException("Negative distance not allowed: check numbers precision.");
        }
        target.setReferenceMeasure(raw.value);
		return target;
	}


}
