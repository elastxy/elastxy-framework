/*
 * Experiment.java
 *
 * Created on 5 agosto 2007, 14.31
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.stats;

import it.red.algen.AlgParameters;
import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Target;
import it.red.algen.tracking.EnvObserver;
import it.red.algen.tracking.Reporter;




/**
 *
 * @author grossi
 */
public class Experiment {
	private AlgParameters _algParameters;
	private Target _target;
    private EnvFactory _factory;
    private int _maxIterations;
    private int _maxLifetime;
    private Integer _maxIdenticalFitnesses;
    private boolean _verbose;
    private Stats _stats;
    private Reporter _reporter;
    
    public Experiment(AlgParameters algParameters, Target target, EnvFactory factory, int maxIterations, int maxLifetime, Integer maxIdenticalFitnesses, boolean verbose, Reporter reporter) {
    	_algParameters = algParameters;
    	_target = target;
    	_maxIterations = maxIterations;
        _maxLifetime = maxLifetime;
        _maxIdenticalFitnesses = maxIdenticalFitnesses;
        _factory = factory;
        _verbose = verbose;
        _reporter = reporter;
    }
    
    public Stats getStats(){
        return _stats;
    }
    
    public void run(){
        EnvObserver observer = new EnvObserver(_verbose, _reporter);
        Env environment = _factory.create(_algParameters, _target, _maxIterations, _maxLifetime, _maxIdenticalFitnesses);
        environment.subscribe(observer);
        
        // Avvia l'evoluzione
        environment.evolve();
        _stats = environment.getStats();
    }
}
