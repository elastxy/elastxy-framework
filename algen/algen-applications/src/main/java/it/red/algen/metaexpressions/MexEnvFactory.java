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
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.StandardMetadataGenoma;

/**
 *
 * @author grossi
 */
@Component
public class MexEnvFactory extends AbstractEnvFactory<PerformanceTarget, BigDecimal, StandardMetadataGenoma> {
	
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private MexSolutionFactory solutionsFactory;
	
	@Autowired private MexGenomaProvider genomaProvider;
	

	@Override
	protected GenomaProvider getGenomaProvider() {
		return genomaProvider;
	}

	@Override
	protected Target<PerformanceTarget, BigDecimal> defineTarget(Genoma genoma) {
		AlgorithmContext context = contextSupplier.getContext();
        
		// Define boundaries
		long maxOperandValue = context.applicationSpecifics.getParamLong(MexApplication.MAX_OPERAND_VALUE);
        Solution minSol = solutionsFactory.createPredefined((StandardMetadataGenoma)genoma, Arrays.asList(maxOperandValue, '*', -maxOperandValue));
        Solution maxSol = solutionsFactory.createPredefined((StandardMetadataGenoma)genoma, Arrays.asList(maxOperandValue, '*', maxOperandValue));

		// Defines goal representation
        Long target = context.applicationSpecifics.getTargetLong(MexApplication.TARGET_EXPRESSION_RESULT);
        PerformanceTarget exprTarget = new PerformanceTarget();
        exprTarget.setGoal(target);
        exprTarget.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);

        // Determines goal rough measure by deriving from extreme solutions
        // TODOM concept of boundaries
        Long minSolPerformance = (Long) context.incubator.grow(minSol.getGenotype(), null).getValue();
        Long maxSolPerformance = (Long) context.incubator.grow(maxSol.getGenotype(), null).getValue();
        NumberRawFitness raw = new NumberRawFitness(
        		new BigDecimal(Math.max(target-minSolPerformance, maxSolPerformance-target)));
        if(raw.value.doubleValue() < 0){
        	throw new RuntimeException("Negative distance not allowed: check numbers precision.");
        }
        exprTarget.setReferenceMeasure(raw.value);
		return exprTarget;
	}


	@Override
	protected SolutionsFactory<StandardMetadataGenoma> getSolutionsFactory() {
		return this.solutionsFactory;
	}

}
