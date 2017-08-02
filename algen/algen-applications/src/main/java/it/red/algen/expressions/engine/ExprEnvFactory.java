/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.domain.Env;
import it.red.algen.domain.Population;
import it.red.algen.engine.EnvFactory;
import it.red.algen.expressions.domain.ExprRawFitness;
import it.red.algen.expressions.domain.ExprSolution;
import it.red.algen.expressions.domain.ExprTarget;

/**
 *
 *TODO: move all factories in a package
 * @author grossi
 */
@Component
public class ExprEnvFactory implements EnvFactory {
	
	
	@Autowired
	private ExprPopulationFactory populationFactory;
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private ExprSolutionFactory solutionsFactory;
	
	
    public Env create(){
        
    	// Crea la popolazione iniziale
        Population startGen = populationFactory.createNew();
        
        // Definisce l'ambiente di riproduzione
        AlgorithmContext context = contextSupplier.getContext();
        int maxOperandValue = context.applicationSpecifics.getParamInteger(ExprApplication.MAX_OPERAND_VALUE);
        ExprSolution minSol = solutionsFactory.create(maxOperandValue, '*', -maxOperandValue);
        ExprSolution maxSol = solutionsFactory.create(maxOperandValue, '*', maxOperandValue);

        // Definisce il target
        Integer target = context.applicationSpecifics.getTargetInteger(ExprApplication.TARGET_EXPRESSION_RESULT);
        ExprTarget exprTarget = new ExprTarget(target);
        // Raw Fitness
        ExprRawFitness raw = new ExprRawFitness(Math.max(target-minSol.compute(), maxSol.compute()-target));
        if(raw.distance < 0){
        	throw new RuntimeException("Negative distance not allowed: check numbers precision.");
        }
        exprTarget.setRawFitness(raw);
        
        // Crea l'ambiente
        Env env = new Env(exprTarget, startGen);
        
        return env;
    }


}
