package it.red.algen.d.metaexpressions;

import java.math.BigDecimal;

import it.red.algen.applications.ApplicationException;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.metaexpressions.MexConstants;

public class TargetBuilder {

	public static Target<PerformanceTarget, BigDecimal> build(AlgorithmContext context) {
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
