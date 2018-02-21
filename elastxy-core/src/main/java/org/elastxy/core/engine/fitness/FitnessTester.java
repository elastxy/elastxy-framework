package org.elastxy.core.engine.fitness;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.tracking.EnvObservable;

public interface FitnessTester extends EnvObservable {


    /** Per ogni soluzione, calcola il valore di fitness e tiene memorizzata la migliore.
     * 
     */
    public Fitness test(Population population, Env env);
    
}
