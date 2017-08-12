package it.red.algen.engine;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Target;

/**
 * Calculates the measure of the target performance
 * @author red
 *
 */
public interface TargetMeasureCalculator {
	public Object calculate(Target target, Env environment);
}
