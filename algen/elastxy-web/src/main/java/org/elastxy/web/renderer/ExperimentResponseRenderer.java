package org.elastxy.web.renderer;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.web.controller.ExperimentResponse;

/**
 * Interface defining the contract for rendering
 * a generic response to clients.
 * 
 * @author red
 *
 */
public interface ExperimentResponseRenderer {

	/**
	 * Render successful experiment stats.
	 * @param stats
	 * @return
	 */
	public ExperimentResponse render(AlgorithmContext context, ExperimentStats stats);

	
	/**
	 * Render generic successful experiment details when no stats 
	 * are provided (e.g. analysis services).
	 * 
	 * @param stats
	 * @return
	 */
	public ExperimentResponse render(AlgorithmContext context, String content);
	
	
	/**
	 * Render error experiment stats.
	 * @param stats
	 * @return
	 */
	public ExperimentResponse render(AlgorithmContext context, Throwable t);
}
