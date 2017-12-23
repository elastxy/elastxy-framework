package org.elastxy.distributed.tracking;

import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.DefaultResultsRenderer;
import org.elastxy.distributed.stats.MultiColonyExperimentStats;

public class DistributedResultsRenderer extends DefaultResultsRenderer{



	@Override
	public ClientFriendlyResults renderFriendly(ExperimentStats stats) {
		MultiColonyExperimentStats mstats = (MultiColonyExperimentStats)stats;
		ClientFriendlyResults result = new ClientFriendlyResults();
		result.goalReached = mstats.targetReached;
		
		result.accuracy = mstats.bestMatch.getFitness().getValue().doubleValue();

		result.iterationsNumber = mstats.eras;
		result.totalExecutionTimeMs = mstats.executionTimeMs;
		
		// default representation is toString()
		result.bestMatch = solutionRenderer.render(mstats.bestMatch).toString();
		result.binaryResult = null;
		result.stringResult = null;
		result.notes = null;
		return result;
	}
}
