package org.elastxy.core.tracking;

import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.TechnicalResults;

public interface ResultsRenderer {
	
//	public void setup(AlgorithmContext context);

	public void setSolutionRenderer(SolutionRenderer solutionRenderer);
	
	public TechnicalResults renderTechie(ExperimentStats stats);
	
	public ClientFriendlyResults renderFriendly(ExperimentStats stats);

}
