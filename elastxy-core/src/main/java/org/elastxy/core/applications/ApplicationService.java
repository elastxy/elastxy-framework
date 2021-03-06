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
package org.elastxy.core.applications;

import org.elastxy.core.applications.components.AppComponentsLocator;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.context.ContextBuilder;
import org.elastxy.core.context.RequestContext;
import org.elastxy.core.engine.core.Experiment;
import org.elastxy.core.engine.core.SingleColonyExperiment;
import org.elastxy.core.engine.operators.UniformlyDistributedSelector;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.stats.StatsExperimentExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationService {
	private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);

	@Autowired private ContextBuilder contextBuilder;
	
	@Autowired private AppComponentsLocator appComponentsLocator;


	
	
	public ExperimentStats executeExperiment(AlgorithmContext context){
	 	setupContext(context);
	 	Experiment e = new SingleColonyExperiment(context);
        e.run();
        ExperimentStats stats = e.getStats();
        return stats;
	}
	
	
	public ExperimentStats executeCheck(AlgorithmContext context){
		RequestContext originContext = context.requestContext;
		context = contextBuilder.build(context.application.appName, AppStage.APPCHECK);
		context.requestContext = originContext;
		setupContext(context);
		Experiment e = new SingleColonyExperiment(context);
		e.run();
		ExperimentStats stats = e.getStats();
        return stats;
	}
	
	
	public ExperimentStats executeBenchmark(AlgorithmContext context){
		// Overwrite initial context with benchmark, plus parameters from request
		RequestContext originContext = context.requestContext;
		context = contextBuilder.build(context.application.appName, AppStage.BENCHMARK);
		context.requestContext = originContext;
		setupContext(context);
		Experiment e = new SingleColonyExperiment(context);
		e.run();
		ExperimentStats stats = e.getStats();
        return stats;
	}
	
	
	public String executeAnalysis(AlgorithmContext context, int experiments){
	 	setupContext(context);
        StatsExperimentExecutor collector = new StatsExperimentExecutor(context, experiments);
        collector.run();
        String result = collector.print();
        return result;
	}
	
	
	public String executeTrialTest(AlgorithmContext context, int experiments){
	 	setupContext(context);
		
		// Trial parameters
		context.algorithmParameters.randomEvolution = true;
 		context.algorithmParameters.mutationPerc = 0.0;
 		context.algorithmParameters.recombinationPerc = 0.0;
 		context.algorithmParameters.crossoverPointRandom = false;
 		context.algorithmParameters.initialSelectionRandom = true;
 		
 		context.algorithmParameters.elitism.singleColonyElitism = false;
 		
 		// Substitute Selector bean with uniform random pick
 		context.application.selector = new UniformlyDistributedSelector();
		context.application.selector.setup(context);

		// Experiments run
        StatsExperimentExecutor collector = new StatsExperimentExecutor(context, experiments);
        collector.run();
        
        String result = collector.print();
        
		
		// TODO2-2: execute test trial beside normal analysis and compare results
//		StringBuffer result = new StringBuffer();
//		result
//		.append("\n\n*********** NORMAL TEST ***********")
//		.append(executeAnalysis(envFactory(), context, experiments))
//		.append("*********** RANDOM TEST ***********")
//		.append(executeTrialTest(envFactory(), context, experiments));
//		return result.toString();

        
        return result;
	}
	

	private void setupContext(AlgorithmContext context) {
		context.application = appComponentsLocator.get(context.application.appName);
		// in distributed context can be null
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context); 
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.recombinator.setup(context.algorithmParameters);
		context.application.targetBuilder.setup(context);
		context.application.envFactory.setup(context);
//		context.application.resultsRenderer.setup(context);
		
		// Distributed application
		if(context.application.multiColonyEnvFactory!=null) context.application.multiColonyEnvFactory.setup(context);
		if(context.application.distributedDatasetProvider!=null) context.application.distributedDatasetProvider.setup(context);
		if(context.application.singleColonyDatasetProvider!=null) context.application.singleColonyDatasetProvider.setup(context);
		if(context.application.distributedGenomaProvider!=null) context.application.distributedGenomaProvider.setup(context);
	}
	

}
