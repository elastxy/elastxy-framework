package it.red.algen.engine.fitness;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Fitness;
import it.red.algen.domain.experiment.Population;
import it.red.algen.tracking.EnvObservable;

public interface FitnessTester extends EnvObservable {


    /** Per ogni soluzione, calcola il valore di fitness e tiene memorizzata la migliore.
     * 
     */
    public Fitness test(Population population, Env env);
    
}
