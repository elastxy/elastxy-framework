/*
 * EnvObserver.java
 *
 * Created on 4 agosto 2007, 14.06
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.tracking;

import java.util.List;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.stats.ExperimentStats;



/** This class processes many events in the lifecycle of the execution,
 *  for the purpose of collecting stats and so on.
 *  
 *  TODOM-4: use generic observer mechanism, such as following Spring:
 *  	- ApplicationEventPublisherAware for publishing events
 *  	- @EventListener annotation for consuming events
 *
 * @author grossi
 */
public class EnvObserver {
	
    private AlgorithmContext context;
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
     * TODOM-4: Incapsulate events under a simple generic interface, 
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
    
    private void showResults(ExperimentStats stats){
    	Logger log = context.monitoringConfiguration.logger;
        log.out("\n##################### STATS #####################");
        log.out("Best match:");
        log.out(renderer.render(stats.lastGeneration.bestMatch));
        log.out("Number of generations: "+stats.generations);
        log.out("Total time (ms): "+stats.time);
        if(context.algorithmParameters.elitarism) {
        	log.out("Total generations with same fitness: "+stats.totIdenticalFitnesses);
        }
        
        if(context.monitoringConfiguration.traceHistory) {
            log.out("\n===================== HISTORY =====================");
            log.out("History of generations");
            List<Population> generations = stats.generationHistory;
            for(int i=0; i < generations.size(); i++){
            	log.out(String.format("Generation [%d] => Best match %s", i+1, generations.get(i).bestMatch));
            }
        }
        
        if(context.monitoringConfiguration.verbose && context.monitoringConfiguration.reporter!=null) {
        	context.monitoringConfiguration.reporter.createReports(stats);
        }
    }
    
}
