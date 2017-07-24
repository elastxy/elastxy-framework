/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Population;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;

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
	private ExprGenesFactory genesFactory;
	
	
    public Env create(){
        
    	// Crea la popolazione iniziale
        Population startGen = populationFactory.createNew();
        
        // Definisce l'ambiente di riproduzione
        AlgorithmContext context = contextSupplier.getContext();
        int maxOperandValue = context.applicationSpecifics.getParamInteger(ExprConf.MAX_OPERAND_VALUE);
        ExprSolution minSol = new ExprSolution(genesFactory, maxOperandValue, '*', -maxOperandValue);
        ExprSolution maxSol = new ExprSolution(genesFactory, maxOperandValue, '*', maxOperandValue);

        // Definisce il target
        Integer target = context.applicationSpecifics.getTargetInteger(ExprConf.TARGET_EXPRESSION_RESULT);
        ExprTarget exprTarget = new ExprTarget(target);
        // Raw Fitness
        ExprRawFitness raw = new ExprRawFitness(Math.max(target-minSol.compute(), maxSol.compute()-target));
        if(raw.distance < 0){
        	throw new RuntimeException("Negative distance not allowed: check numbers precision.");
        }
        exprTarget.setRawFitness(raw);
        
        // Crea l'ambiente
        Env env = new Env();
        env.init(context, startGen, exprTarget);
        
        return env;
    }


}
