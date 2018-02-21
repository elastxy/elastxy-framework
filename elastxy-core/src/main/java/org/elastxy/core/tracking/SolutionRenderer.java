package org.elastxy.core.tracking;

import org.elastxy.core.domain.experiment.Solution;

/**
 * Renders Solution in a type R
 * @author red
 *
 * @param <R>
 */
public interface SolutionRenderer<R> {

	public R render(Solution solution);

}
