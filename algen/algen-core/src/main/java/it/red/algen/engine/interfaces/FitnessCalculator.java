package it.red.algen.engine.interfaces;

import it.red.algen.domain.interfaces.Fitness;
import it.red.algen.domain.interfaces.Solution;
import it.red.algen.domain.interfaces.Target;

public interface FitnessCalculator<S extends Solution, T extends Target, F extends Fitness, P> {

    public F calculate(S solution, T target);
    
    public P perform(S solution);
}
