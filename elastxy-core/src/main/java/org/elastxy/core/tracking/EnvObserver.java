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

import java.util.List;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.stats.ExperimentStats;



/** This class processes many events in the lifecycle of the execution,
 *  for the purpose of collecting stats and so on.
 *  
 *  TODO3-4: use generic observer mechanism, such as following Spring:
 *  	- ApplicationEventPublisherAware for publishing events
 *  	- @EventListener annotation for consuming events
 *
 * @author grossi
 */
public class EnvObserver {
	
    protected AlgorithmContext context;
    private SolutionRenderer renderer = new DefaultSolutionRenderer();
    
    public EnvObserver(AlgorithmContext context){
        this.context = context;
        if(context.application.solutionRenderer!=null){
        	this.renderer = context.application.solutionRenderer;
        }
    }
    
    public void setRenderer(SolutionRenderer renderer){
    	this.renderer = renderer;
    }
    
    public void newGenerationEvent(int number, long executionTime, Population lastGen, Population newGen){
        if(context.monitoringConfiguration.verbose) {
        	context.monitoringConfiguration.logger.out("\n*** Last generation "+number+" > \n"+lastGen+"\n");
        	if(context.application.solutionRenderer!=null && lastGen!=null && lastGen.bestMatch!=null){
        		String sol = (String)context.application.solutionRenderer.render(lastGen.bestMatch);
        		context.monitoringConfiguration.logger.out(sol);
        	}
        }
        
        if(context.monitoringConfiguration.showGraph) {
        	int step = ASCIIGraphRenderer.adaptStepToSpeed(number, executionTime);
        	String graphStep = ASCIIGraphRenderer.displayGraph(number, lastGen, step, true);
	        if(graphStep.length()!=0) context.monitoringConfiguration.logger.out(graphStep);
        }
    }


    
    /**
     * TODO3-4: Incapsulate events under a simple generic interface, 
     * such as Spring "publishEvent" of ApplicationEventPublisher
     * @param s
     */
    public void fitnessCalculatedEvent(Solution s){
//        if(_verbose) LoggerManager.instance().out(s);
    }
    
    public void illegalSolutionEvent(Solution s){
        if(context.monitoringConfiguration.verbose) context.monitoringConfiguration.logger.out("!    "+s+" Soluzione non ammessa. "+s.getFitness().getLegalCheck());
    }
    
    public void crossoverEvent(Solution father, Solution mother, List<Solution> sons){
        if(context.monitoringConfiguration.verbose) context.monitoringConfiguration.logger.out("XXX CROSSOVER: \n"+father+"\n"+mother+"\n\t->\n"+sons.get(0)+"\n"+sons.get(1));
    }
    
    public void mutationEvent(Solution original, Solution mutated){
        if(context.monitoringConfiguration.verbose) context.monitoringConfiguration.logger.out("+++ MUTAZIONE: \n"+original+"\n\t-> \n"+mutated);
    }
    
    public void goalReachedEvent(ExperimentStats stats){
    	context.monitoringConfiguration.logger.out("******* SUCCESS *******");
        showResults(stats);
    }
    
    public void stableSolutionEvent(ExperimentStats stats){
    	context.monitoringConfiguration.logger.out("******* STABLE SOLUTION *******");
        showResults(stats);
    }
    
    public void historyEndedEvent(ExperimentStats stats){
    	context.monitoringConfiguration.logger.out("--- STORY HAS ENDED WITHOUT REACHING GOAL... ---");
        showResults(stats);
    }
    
    
    // TODO2-2: ResultsRenderer: put into a ResultsRenderer
    private void showResults(ExperimentStats stats){
    	Logger log = context.monitoringConfiguration.logger;
        log.out("\n##################### STATS #####################");
        log.out("Best match:");
        log.out(renderer.render(stats.bestMatch));
        log.out("Other best matches number:");
        log.out(stats.lastGeneration.bestMatches==null?0:stats.lastGeneration.bestMatches.size());
        log.out("Number of generations: "+stats.generations);
        log.out("Total execution time (ms): "+stats.executionTimeMs);
        if(context.algorithmParameters.elitism.singleColonyElitism) {
        	log.out("Total generations with same fitness: "+stats.totIdenticalFitnesses);
        }
        
        if(context.monitoringConfiguration.traceHistory) {
            log.out("\n===================== HISTORY =====================");
            log.out("History of generations");
            List<Population> generations = stats.generationHistory;
            // TODOM-2: increase for loops performance
            int tot = generations.size();
            for(int i=0; i < tot; i++){
            	log.out(String.format("Generation [%d] => Best match %s", i+1, generations.get(i).bestMatch));
            }
        }
        
        if(context.monitoringConfiguration.verbose && context.monitoringConfiguration.reporter!=null) {
        	context.monitoringConfiguration.reporter.createReports(stats);
        }
    }
    
}
