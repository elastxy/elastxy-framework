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
import it.red.algen.Target;
import it.red.algen.context.ContextSupplier;
import it.red.algen.tracking.Logger;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;



/**
 *
 * @author grossi
 */
public class StatsExperimentExecutor {
    
	@Autowired
	private EnvFactory _envFactory;
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
    private int _experiments;
    private Target _target;
    
    private AggregatedStats _globalStats;
    
    public StatsExperimentExecutor(int experiments, Target target){
        _experiments = experiments;
        _target = target;
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
            Experiment e = new Experiment(_target, _envFactory);
            beanFactory.autowireBean(e);
            e.run();
            addStats(e.getStats());
        }
    }
    
    public void print(){
        Logger logger = LoggerManager.instance();
        logger.out("\n\n@@@@@@@@@@@@  GLOBAL STATS @@@@@@@@@@@");
        logger.out("EXPERIMENTS: "+_globalStats._totExperiments);
        logger.out("SUCCESSES: "+_globalStats.getPercSuccesses()+"%");
        logger.out("AVG TIME (sec): "+_globalStats.getAvgTime());
        logger.out("AVG GEN: "+_globalStats.getAvgGenerations());
        logger.out("AVG FITNESS: "+_globalStats.getAvgFitness());
        logger.out("MAX FITNESS: "+String.format("%.2f", _globalStats._maxFitness.orElse(null)));
        logger.out("MIN FITNESS: "+String.format("%.2f", _globalStats._minFitness.orElse(null)));
    }
}
