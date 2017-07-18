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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Target;
import it.red.algen.context.ContextSupplier;
import it.red.algen.tracking.EnvObserver;




/**
 *
 * @author grossi
 */
public class Experiment {
	private Target _target;    
	private EnvFactory _factory;
    private ExperimentStats _stats;

//	private @Autowired AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ContextSupplier contextSupplier;
    
    public Experiment(Target target, EnvFactory factory) {
    	_target = target;
        _factory = factory;
        _stats = null;
    }
    
    public ExperimentStats getStats(){
        return _stats;
    }
    
    public void run(){
//        beanFactory.autowireBean(this);
        EnvObserver observer = new EnvObserver(contextSupplier.getContext());
        Env environment = _factory.create(_target);
        environment.subscribe(observer);
        
        // Avvia l'evoluzione
        environment.evolve();
        _stats = environment.getStats();
    }
}
