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

import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Population;
import it.red.algen.Target;

/**
 *
 * @author grossi
 */
public class ExprEnvFactory implements EnvFactory {
    private int targetValue;
    
	public ExprEnvFactory(int targetValue){
		this.targetValue = targetValue;
	}
	
	
    public Env create(int maxIterations, int maxLifetime, Integer maxIdenticalFitnesses){
        // Crea la popolazione iniziale
        Population startGen = ExprPopulationFactory.createNew(ExprConf.INITIAL_POPULATION);
        
        // Definisce l'ambiente di riproduzione
        ExprSolution minSol = new ExprSolution(0, '-', 9);
        ExprSolution maxSol = new ExprSolution(9, '*', 9);
        Target target = new ExprTarget(targetValue, minSol.compute(), maxSol.compute());
        return new Env(startGen, target, maxIterations, maxLifetime, maxIdenticalFitnesses);
    }
    
}
