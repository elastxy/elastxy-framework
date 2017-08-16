package it.red.algen.tracking;

import it.red.algen.domain.experiment.Solution;

/**
 * Renders Solution in a type R
 * @author red
 *
 * @param <R>
 */
public interface SolutionRenderer<R> {

	public R render(Solution solution);

}
