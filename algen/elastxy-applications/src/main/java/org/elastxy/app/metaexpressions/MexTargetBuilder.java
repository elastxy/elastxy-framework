package org.elastxy.app.metaexpressions;

import java.math.BigDecimal;

import org.elastxy.core.applications.ApplicationException;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.NumberRawFitness;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.engine.factory.TargetBuilder;

public class MexTargetBuilder implements TargetBuilder<PerformanceTarget, BigDecimal>{
	private AlgorithmContext context;
	
	@Override
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	public Target<PerformanceTarget, BigDecimal> define(WorkingDataset workingDataset) {
		// Defines goal representation
        Long targetValue = context.applicationSpecifics.getTargetLong(MexConstants.TARGET_EXPRESSION_RESULT);
        PerformanceTarget target = new PerformanceTarget();
        target.setGoal(targetValue);

        // Determines goal rough measure by deriving from extreme solutions
        // TODOA-8: introduce concepts of boundaries and distance, with specific calculation methods
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