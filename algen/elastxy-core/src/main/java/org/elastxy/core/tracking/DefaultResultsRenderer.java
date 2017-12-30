package org.elastxy.core.tracking;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.TechnicalResults;

public class DefaultResultsRenderer implements ResultsRenderer {
//	private AlgorithmContext context;
	protected SolutionRenderer solutionRenderer;
	
//	@Override
//	public void setup(AlgorithmContext context) {
//		this.context = context;
//	}
	
	@Override
	public void setSolutionRenderer(SolutionRenderer solutionRenderer) {
		this.solutionRenderer = solutionRenderer;
	}


	@Override
	public TechnicalResults renderTechie(ExperimentStats stats) {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public ClientFriendlyResults renderFriendly(ExperimentStats stats) {
		ClientFriendlyResults result = new ClientFriendlyResults();
		result.goalReached = stats.targetReached;
		
		Solution bestMatch = stats.bestMatch;
		result.accuracy = bestMatch.getFitness().getValue().doubleValue();

		result.iterationsNumber = stats.generations;
		result.totalExecutionTimeMs = stats.executionTimeMs;
		
		// default representation is toString()
		result.bestMatch = solutionRenderer.render(bestMatch).toString();
		result.binaryResult = null;
		result.stringResult = null;
		result.notes = null;
		return result;
	}


}
