package it.red.algen.engine;

import it.red.algen.domain.Fitness;
import it.red.algen.domain.Solution;
import it.red.algen.domain.Target;

public interface FitnessCalculator<S extends Solution, T extends Target, F extends Fitness, P> {

    public F calculate(S solution, T target);
    
    public P perform(S solution);
}
