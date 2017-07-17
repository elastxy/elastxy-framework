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

import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Target;
import it.red.algen.conf.AlgorithmContext;
import it.red.algen.tracking.EnvObserver;




/**
 *
 * @author grossi
 */
public class Experiment {
	private AlgorithmContext _context;
	private Target _target;
    private EnvFactory _factory;
    private Stats _stats;
    
    public Experiment(AlgorithmContext context, Target target, EnvFactory factory) {
    	_context = context;
    	_target = target;
        _factory = factory;
    }
    
    public Stats getStats(){
        return _stats;
    }
    
    public void run(){
        EnvObserver observer = new EnvObserver(_context);
        Env environment = _factory.create(_context, _target);
        environment.subscribe(observer);
        
        // Avvia l'evoluzione
        environment.evolve();
        _stats = environment.getStats();
    }
}
