package it.red.algen.engine;

import it.red.algen.domain.Population;
import it.red.algen.domain.interfaces.Fitness;
import it.red.algen.domain.interfaces.Target;
import it.red.algen.tracking.EnvObservable;

public interface FitnessTester extends EnvObservable {


    /** Per ogni soluzione, calcola il fitness e tiene memorizzata la migliore.
     * 
     */
    public Fitness test(Target<?> target, Population population);
    
}
