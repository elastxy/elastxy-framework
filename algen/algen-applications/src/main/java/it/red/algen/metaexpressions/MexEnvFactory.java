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
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;

/**
 *
 * @author grossi
 */
@Component
public class MexEnvFactory implements EnvFactory {
	
	
	@Autowired
	private PopulationFactory populationFactory;
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private MexSolutionFactory solutionsFactory;
	
	
    public Env create(){
        
    	// Create initial population
        Population startGen = populationFactory.createNew();
        
        // Define evolution environment
        AlgorithmContext context = contextSupplier.getContext();
        long maxOperandValue = context.applicationSpecifics.getParamLong(MexApplication.MAX_OPERAND_VALUE);
        Solution minSol = solutionsFactory.createPredefined(Arrays.asList(maxOperandValue, '*', -maxOperandValue));
        Solution maxSol = solutionsFactory.createPredefined(Arrays.asList(maxOperandValue, '*', maxOperandValue));

        // Defines goal representation
        Long target = context.applicationSpecifics.getTargetLong(MexApplication.TARGET_EXPRESSION_RESULT);
        PerformanceTarget exprTarget = new PerformanceTarget();
        exprTarget.setGoal(target);

        // Determines goal rough measure by deriving from extreme solutions
        // TODOM concept of boundaries
        Long minSolPerformance = (Long) context.incubator.grow(minSol.getGenotype()).getValue();
        Long maxSolPerformance = (Long) context.incubator.grow(maxSol.getGenotype()).getValue();
        NumberRawFitness raw = new NumberRawFitness(
        		new BigDecimal(Math.max(target-minSolPerformance, maxSolPerformance-target)));
        if(raw.value.doubleValue() < 0){
        	throw new RuntimeException("Negative distance not allowed: check numbers precision.");
        }
        exprTarget.setMeasure(raw.value);
        
        // Creates environment
        Env env = new Env(exprTarget, startGen);
        
        return env;
    }


}
