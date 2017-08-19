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
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;

/**
 *
 * @author grossi
 */
@Component
public class MexEnvFactory implements EnvFactory {
	
	
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private PopulationFactory populationFactory;
	
	@Autowired private MexSolutionFactory solutionsFactory;
	
	@Autowired private MexGenomaProvider genomaProvider;
	
    public Env create(){

    	// Define target
    	PerformanceTarget target = defineTarget();
    	
    	// Create genoma
    	Genoma genoma = createGenoma(target);

    	// Create initial population
    	Population startGen = createInitialPopulation();
        
        // Create environment
        Env env = new Env(target, startGen);
        
        return env;
    }


	private Genoma createGenoma(Target target) {
//    	genomaProvider.reduce(target);
		Genoma genoma = genomaProvider.collect();
		contextSupplier.getContext().mutator.setGenoma(genoma);
		return genoma;
	}


	private Population createInitialPopulation() {
		populationFactory.setSolutionsFactory(solutionsFactory);
        Population startGen = populationFactory.createNew();
		return startGen;
	}


	private PerformanceTarget defineTarget() {
    	AlgorithmContext context = contextSupplier.getContext();
        
		// Define boundaries
		long maxOperandValue = context.applicationSpecifics.getParamLong(MexApplication.MAX_OPERAND_VALUE);
        Solution minSol = solutionsFactory.createPredefined(Arrays.asList(maxOperandValue, '*', -maxOperandValue));
        Solution maxSol = solutionsFactory.createPredefined(Arrays.asList(maxOperandValue, '*', maxOperandValue));

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


}
