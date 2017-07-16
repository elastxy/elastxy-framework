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

import it.red.algen.AlgParameters;
import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Population;
import it.red.algen.Target;

/**
 *
 * @author grossi
 */
@Component
public class ExprEnvFactory implements EnvFactory {
	
	@Autowired
	private ExprPopulationFactory populationFactory;
	
	
    public Env create(AlgParameters algParameters, Target target, int maxIterations, int maxLifetime, Integer maxIdenticalFitnesses){
        // Crea la popolazione iniziale
        Population startGen = populationFactory.createNew(algParameters, ExprConf.INITIAL_POPULATION);
        
        // Definisce l'ambiente di riproduzione
        ExprSolution minSol = new ExprSolution(0, '-', 9);
        ExprSolution maxSol = new ExprSolution(9, '*', 9);
        ExprTarget exprTarget = new ExprTarget(((ExprTarget)target).getComputeValue(), minSol.compute(), maxSol.compute());
        // TODOA: add Contraints, StopCondition
        
        Env env = new Env();
        env.init(algParameters, startGen, exprTarget, maxIterations, maxLifetime, maxIdenticalFitnesses);
        return env;
    }
    
}
