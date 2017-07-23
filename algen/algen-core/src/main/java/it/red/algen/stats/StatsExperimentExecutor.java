/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.stats;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import it.red.algen.EnvFactory;
import it.red.algen.tracking.Logger;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;



/**
 *
 * @author grossi
 */
public class StatsExperimentExecutor {
    
	private EnvFactory _envFactory;
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
    private int _experiments;
    
    private AggregatedStats _globalStats;
    
    public StatsExperimentExecutor(EnvFactory envFactory, int experiments){
        _envFactory = envFactory;
        _experiments = experiments;
        _globalStats = new AggregatedStats();
        _globalStats._totExperiments = _experiments;
    }
    
    private void addStats(ExperimentStats stats){
        _globalStats._totTime += stats._time;
        _globalStats._totGenerations += stats._generations;
        _globalStats._totSuccesses += (stats._lastGeneration.isGoalReached() ? 1 : 0);
        double bestMatchFitness = stats._lastGeneration.getBestMatch().getFitness().getValue();
        _globalStats._totFitness += bestMatchFitness;
        _globalStats._maxFitness = Optional.of(_globalStats._minFitness.isPresent() ? Math.max(_globalStats._minFitness.get(), bestMatchFitness) : bestMatchFitness);
        _globalStats._minFitness = Optional.of(_globalStats._minFitness.isPresent() ? Math.min(_globalStats._minFitness.get(), bestMatchFitness) : bestMatchFitness);
    }
    
    public void run(){
        LoggerManager.instance().init(new SimpleLogger());
        for(int i = 0; i < _experiments; i++){
            Experiment e = new Experiment(_envFactory);
            beanFactory.autowireBean(e);
            e.run();
            addStats(e.getStats());
        }
    }
    
    public String print(){
    	StringBuffer buffer = new StringBuffer();
    	
        Logger logger = LoggerManager.instance();
        out(logger, buffer, "\n\n@@@@@@@@@@@@  GLOBAL STATS @@@@@@@@@@@");
        out(logger, buffer, "EXPERIMENTS: "+_globalStats._totExperiments);
        out(logger, buffer, "SUCCESSES: "+_globalStats.getPercSuccesses()+"%");
        out(logger, buffer, "AVG TIME (sec): "+_globalStats.getAvgTime());
        out(logger, buffer, "AVG GEN: "+_globalStats.getAvgGenerations());
        out(logger, buffer, "AVG FITNESS: "+_globalStats.getAvgFitness());
        out(logger, buffer, "MAX FITNESS: "+String.format("%.2f", _globalStats._maxFitness.orElse(null)));
        out(logger, buffer, "MIN FITNESS: "+String.format("%.2f", _globalStats._minFitness.orElse(null)));
        
        return buffer.toString();
    }
    
    // TODOM: logging serio
    private void out(Logger logger, StringBuffer buffer, String msg){
    	logger.out(msg);
    	buffer.append(msg).append(System.getProperty("line.separator"));
    }
}
