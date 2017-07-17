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

import it.red.algen.Env;
import it.red.algen.Population;
import it.red.algen.Solution;
import it.red.algen.conf.AlgorithmContext;
import it.red.algen.stats.ExperimentStats;



/** Questa classe raccoglie statistiche sulla base di eventi del sistema.
 *
 *
 * @author grossi
 */
public class EnvObserver {
	
    private AlgorithmContext _context;
    
    public EnvObserver(AlgorithmContext context){
        _context = context;
    }
    
    public void newGenerationEvent(int number, Population newGen){
        if(_context.monitoringConfiguration.verbose) LoggerManager.instance().out("\n*** Nuova generazione "+number+" > \n"+newGen+"\n");
    }
    
    public void fitnessCalculatedEvent(Solution s){
//        if(_verbose) LoggerManager.instance().out(s);
    }
    
    public void illegalSolutionEvent(Solution s){
        if(_context.monitoringConfiguration.verbose) LoggerManager.instance().out("!    "+s+" Soluzione non ammessa. "+s.legalCheck());
    }
    
    public void crossoverEvent(Solution father, Solution mother, Solution[] sons){
        if(_context.monitoringConfiguration.verbose) LoggerManager.instance().out("XXX CROSSOVER: \n"+father+"\n"+mother+"\n\t->\n"+sons[0]+"\n"+sons[1]);
    }
    
    public void mutationEvent(Solution original, Solution mutated){
        if(_context.monitoringConfiguration.verbose) LoggerManager.instance().out("+++ MUTAZIONE: \n"+original+"\n\t-> \n"+mutated);
    }
    
    public void goalReachedEvent(Env environment){
        LoggerManager.instance().out("******* SUCCESS *******");
        showResults(environment);
    }
    
    public void stableSolutionEvent(Env environment){
        LoggerManager.instance().out("******* STABLE SOLUTION *******");
        showResults(environment);
    }
    
    public void historyEndedEvent(Env environment){
        LoggerManager.instance().out("--- STORY HAS ENDED WITHOUT REACHING GOAL... ---");
        showResults(environment);
    }
    
    private void showResults(Env environment){
        LoggerManager log = LoggerManager.instance();
        log.out("\n##################### STATS #####################");
        ExperimentStats stats = environment.getStats();
        log.out("Best match:");
        log.out(stats._lastGeneration.getBestMatch());
        log.out("Number of generations: "+stats._generations);
        log.out("Total time (sec): "+stats._time);
        if(environment.getContext().parameters.getElitarism()) {
        	log.out("Total generations with same fitness: "+stats._totIdenticalFitnesses);
        }
        
        if(_context.monitoringConfiguration.reporter!=null) {
        	_context.monitoringConfiguration.reporter.createReports(stats);
        }
        
//        log.out("History of generations");
//        List generations = stats._generationsHistory;
//        for(int i = 0; i < generations.size(); i++){
//            Population p = (Population)generations.get(i);
//            log.out(p.getBestMatch());
//        }
    }
    
}
