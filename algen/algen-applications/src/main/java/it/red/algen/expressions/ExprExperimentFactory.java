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

import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentFactory;
import it.red.algen.tracking.Reporter;

/**
 *
 * @author grossi
 */
public class ExprExperimentFactory implements ExperimentFactory {
    private int _maxIterations;
    private int _maxLifetime;
    private Integer _maxIdenticalFitnesses;
    private boolean _verbose;
    private Reporter _reporter;
    
    public ExprExperimentFactory(int maxIterations, int maxLifetime, Integer _maxIdenticalFitnesses, boolean verbose, Reporter reporter){
        _maxIterations = maxIterations;
        _maxLifetime = maxLifetime;
        _verbose = verbose;
        _reporter = reporter;
    }
    
    public Experiment create(){
        return new Experiment(new ExprEnvFactory(ExprConf.TARGET), _maxIterations, _maxLifetime, _maxIdenticalFitnesses, _verbose, _reporter);
    }
    
}
