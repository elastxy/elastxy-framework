package it.red.algen.engine;

import it.red.algen.domain.Fitness;
import it.red.algen.domain.Population;
import it.red.algen.domain.Target;
import it.red.algen.tracking.EnvObservable;

public interface FitnessTester extends EnvObservable {


    /** Per ogni soluzione, calcola il fitness e tiene memorizzata la migliore.
     * 
     */
    public Fitness testFitness(Target target, Population population);
    
}
