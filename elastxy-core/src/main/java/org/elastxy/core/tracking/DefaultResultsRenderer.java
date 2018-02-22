/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.elastxy.core.tracking;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.StandardExperimentResults;
import org.elastxy.core.stats.TechnicalResults;

public class DefaultResultsRenderer implements ResultsRenderer {
//	private AlgorithmContext context;
	protected SolutionRenderer techieSolutionRenderer;
	protected SolutionRenderer friendlySolutionRenderer;
	
//	@Override
//	public void setup(AlgorithmContext context) {
//		this.context = context;
//	}

	@Override
	public void setTechieSolutionRenderer(SolutionRenderer solutionRenderer) {
		this.techieSolutionRenderer = solutionRenderer;
	}
	
	@Override
	public void setFriendlySolutionRenderer(SolutionRenderer solutionRenderer) {
		this.friendlySolutionRenderer = solutionRenderer;
	}


	@Override
	public TechnicalResults renderTechie(ExperimentStats stats) {
		TechnicalResults result = new TechnicalResults();

		setCommonProperties(stats, result);
		
		Solution bestMatch = stats.bestMatch;
		result.fitnessValue = bestMatch.getFitness().getValue().doubleValue();
		result.rawFitnessValue = bestMatch.getFitness().getRawValue();
		result.legalCheck = bestMatch.getFitness().getLegalCheck();
		result.phenotypeValue = bestMatch.getPhenotype().getValue();
		
		return result;
	}

	@Override
	public ClientFriendlyResults renderFriendly(ExperimentStats stats) {
		
		ClientFriendlyResults result = new ClientFriendlyResults();
		
		Solution bestMatch = setCommonProperties(stats, result);
		
		// default representation is toString()
		result.accuracy = bestMatch.getFitness().getValue().doubleValue() * 100.0;
		result.stringResult = friendlySolutionRenderer.render(bestMatch).toString();
		result.binaryResult = null;
		
		result.notes = null;
		
		return result;
	}

	private Solution setCommonProperties(ExperimentStats stats, StandardExperimentResults result) {
		result.goalReached = stats.targetReached;
		Solution bestMatch = stats.bestMatch;
		result.iterationsNumber = stats.generations;
		result.totalExecutionTimeMs = stats.executionTimeMs;
		return bestMatch;
	}


}
