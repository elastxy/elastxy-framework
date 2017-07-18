/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.31
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import it.red.algen.Target;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentFactory;

/**
 *
 * @author grossi
 */
public class ExprExperimentFactory implements ExperimentFactory {
    
	// TODOM: bind AlgorithmContext to threadlocal
    public Experiment create(AlgorithmContext context, Target target){
        return new Experiment(context, target, new ExprEnvFactory());
    }
    
}
