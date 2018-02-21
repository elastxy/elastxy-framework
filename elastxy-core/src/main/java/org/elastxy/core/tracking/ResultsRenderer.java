package org.elastxy.core.tracking;

import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.TechnicalResults;

/**
 * An interface for providing both technical and user friendly
 * experiment outcomes rendering.
 * 
 * Solution renderer can be different in either cases.
 * If only one is specified, by default that's used.
 * 
 * @author red
 *
 */
public interface ResultsRenderer {
	
//	public void setup(AlgorithmContext context);

	public void setTechieSolutionRenderer(SolutionRenderer solutionRenderer);

	public void setFriendlySolutionRenderer(SolutionRenderer solutionRenderer);
	
	public TechnicalResults renderTechie(ExperimentStats stats);
	
	public ClientFriendlyResults renderFriendly(ExperimentStats stats);

}
