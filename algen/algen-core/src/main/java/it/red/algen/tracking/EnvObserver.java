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
import it.red.algen.engine.Evolver;
import it.red.algen.stats.ExperimentStats;



/** Questa classe raccoglie statistiche sulla base di eventi del sistema.
 *
 *
 * @author grossi
 */
public class EnvObserver {
	
    private AlgorithmContext context;
    private SolutionRenderer renderer = new DefaultSolutionRenderer();
    
    public EnvObserver(AlgorithmContext context){
        this.context = context;
        if(context.monitoringConfiguration.solutionRenderer!=null){
        	this.renderer = context.monitoringConfiguration.solutionRenderer;
        }
    }
    
    public void setRenderer(SolutionRenderer renderer){
    	this.renderer = renderer;
    }
    
    public void newGenerationEvent(int number, Population newGen){
        if(context.monitoringConfiguration.verbose) context.monitoringConfiguration.logger.out("\n*** Nuova generazione "+number+" > \n"+newGen+"\n");
    }
    
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
    
    public void goalReachedEvent(Evolver evolver){
    	context.monitoringConfiguration.logger.out("******* SUCCESS *******");
        showResults(evolver);
    }
    
    public void stableSolutionEvent(Evolver evolver){
    	context.monitoringConfiguration.logger.out("******* STABLE SOLUTION *******");
        showResults(evolver);
    }
    
    public void historyEndedEvent(Evolver evolver){
    	context.monitoringConfiguration.logger.out("--- STORY HAS ENDED WITHOUT REACHING GOAL... ---");
        showResults(evolver);
    }
    
    private void showResults(Evolver evolver){
    	Logger log = context.monitoringConfiguration.logger;
        log.out("\n##################### STATS #####################");
        ExperimentStats stats = evolver.getStats();
        log.out("Best match:");
        log.out(renderer.render(stats.lastGeneration.bestMatch));
        log.out("Number of generations: "+stats.generations);
        log.out("Total time (ms): "+stats.time);
        if(context.parameters.elitarism) {
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
