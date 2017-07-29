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

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;
import it.red.algen.engine.Evolver;
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
        if(_context.monitoringConfiguration.verbose) _context.monitoringConfiguration.logger.out("\n*** Nuova generazione "+number+" > \n"+newGen+"\n");
    }
    
    public void fitnessCalculatedEvent(Solution s){
//        if(_verbose) LoggerManager.instance().out(s);
    }
    
    public void illegalSolutionEvent(Solution s){
        if(_context.monitoringConfiguration.verbose) _context.monitoringConfiguration.logger.out("!    "+s+" Soluzione non ammessa. "+s.legalCheck());
    }
    
    public void crossoverEvent(Solution father, Solution mother, Solution[] sons){
        if(_context.monitoringConfiguration.verbose) _context.monitoringConfiguration.logger.out("XXX CROSSOVER: \n"+father+"\n"+mother+"\n\t->\n"+sons[0]+"\n"+sons[1]);
    }
    
    public void mutationEvent(Solution original, Solution mutated){
        if(_context.monitoringConfiguration.verbose) _context.monitoringConfiguration.logger.out("+++ MUTAZIONE: \n"+original+"\n\t-> \n"+mutated);
    }
    
    public void goalReachedEvent(Evolver evolver){
    	_context.monitoringConfiguration.logger.out("******* SUCCESS *******");
        showResults(evolver);
    }
    
    public void stableSolutionEvent(Evolver evolver){
    	_context.monitoringConfiguration.logger.out("******* STABLE SOLUTION *******");
        showResults(evolver);
    }
    
    public void historyEndedEvent(Evolver evolver){
    	_context.monitoringConfiguration.logger.out("--- STORY HAS ENDED WITHOUT REACHING GOAL... ---");
        showResults(evolver);
    }
    
    private void showResults(Evolver evolver){
    	Logger log = _context.monitoringConfiguration.logger;
        log.out("\n##################### STATS #####################");
        ExperimentStats stats = evolver.getStats();
        log.out("Best match:");
        log.out(stats._lastGeneration.getBestMatch());
        log.out("Number of generations: "+stats._generations);
        log.out("Total time (sec): "+stats._time);
        if(_context.parameters._elitarism) {
        	log.out("Total generations with same fitness: "+stats._totIdenticalFitnesses);
        }
        
        if(_context.monitoringConfiguration.verbose && _context.monitoringConfiguration.reporter!=null) {
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
