package it.red.algen.metaexpressions;

import java.math.BigDecimal;

import it.red.algen.applications.ApplicationException;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.engine.factory.TargetBuilder;

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
