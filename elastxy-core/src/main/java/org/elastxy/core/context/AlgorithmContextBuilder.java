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
package org.elastxy.core.context;

import org.elastxy.core.conf.AlgorithmParameters;
import org.elastxy.core.conf.ElitismParameters;
import org.elastxy.core.conf.MonitoringConfiguration;
import org.elastxy.core.conf.StopConditions;
import org.elastxy.core.tracking.Reporter;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmContextBuilder {

	public AlgorithmContext build(
			long initialSelectionNumber,
			boolean initialSelectionRandom,
			boolean crossoverPointRandom,
			double recombinationPerc,
			double mutationPerc,
			boolean singleColonyElitism,
			int maxGenerations,
			int maxLifetimeMs,
			int maxIdenticalFitnesses,
			boolean verbose,
			boolean showGraph,
			boolean traceHistory,
			Reporter reporter
			){
		
        AlgorithmParameters algParameters = new AlgorithmParameters();
        algParameters.recombinationPerc = recombinationPerc;
        algParameters.crossoverPointRandom = crossoverPointRandom;
        algParameters.mutationPerc = mutationPerc;
        algParameters.initialSelectionNumber = initialSelectionNumber;
        algParameters.initialSelectionRandom = initialSelectionRandom;
        
        algParameters.elitism = new ElitismParameters();
        algParameters.elitism.singleColonyElitism = singleColonyElitism;
        
        StopConditions stopConditions = new StopConditions();
        stopConditions.maxGenerations = maxGenerations;
        stopConditions.maxLifetimeMs = maxLifetimeMs;
        stopConditions.maxIdenticalFitnesses = maxIdenticalFitnesses;
        
        MonitoringConfiguration monitoringConfiguration = new MonitoringConfiguration();
        monitoringConfiguration.verbose = verbose;
        monitoringConfiguration.showGraph = showGraph;
        monitoringConfiguration.traceHistory = traceHistory;
        monitoringConfiguration.reporter = reporter;
        
        AlgorithmContext context = new AlgorithmContext();
        context.algorithmParameters = algParameters;
        context.algorithmParameters.stopConditions = stopConditions;
        context.monitoringConfiguration = monitoringConfiguration;
        
        return context;
	}
}
