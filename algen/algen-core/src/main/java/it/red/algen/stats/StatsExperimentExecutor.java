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

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import it.red.algen.engine.interfaces.EnvFactory;



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
        
        if(stats.targetReached){
        	_globalStats.successExecutionTimes.add(stats._time);
        	_globalStats._totSuccesses++;;
        }
        _globalStats._totGenerations += stats._generations;
        double bestMatchFitness = stats._lastGeneration.bestMatch.getFitness().getValue();
        _globalStats._totFitness += bestMatchFitness;
        _globalStats._maxFitness = Optional.of(_globalStats._minFitness.isPresent() ? Math.max(_globalStats._minFitness.get(), bestMatchFitness) : bestMatchFitness);
        _globalStats._minFitness = Optional.of(_globalStats._minFitness.isPresent() ? Math.min(_globalStats._minFitness.get(), bestMatchFitness) : bestMatchFitness);
    }
    
    public void run(){
        for(int i = 0; i < _experiments; i++){
            Experiment e = new Experiment(_envFactory);
            beanFactory.autowireBean(e);
            e.run();
            addStats(e.getStats());
        }
        
        calculateGlobalStats();
    }
    
    private void calculateGlobalStats(){
    	
    	// Get a DescriptiveStatistics instance
    	DescriptiveStatistics stats = new DescriptiveStatistics();

    	// Add the data from the array
    	for( int i = 0; i < _globalStats.successExecutionTimes.size(); i++) {
    	        stats.addValue(_globalStats.successExecutionTimes.get(i));
    	}

    	// Compute some statistics
    	_globalStats.mean = Optional.of(stats.getMean());
    	_globalStats.stdDev = Optional.of(stats.getStandardDeviation());
    	_globalStats.median = Optional.of(stats.getPercentile(50.0));
    }
    
    public String print(){
    	StringBuffer buffer = new StringBuffer();
    	
        outln(buffer, "\n\n@@@@@@@@@@@@  GLOBAL STATS @@@@@@@@@@@");
        outln(buffer, "EXPERIMENTS: "+_globalStats._totExperiments);
        outln(buffer, "SUCCESSES: "+_globalStats.getPercSuccesses()+"%");
        outln(buffer, "-- Descriptive Statistics --");
        outln(buffer, "AVG GEN: "+_globalStats.getAvgGenerations());
        outln(buffer, "AVG TIME (ms): "+String.format("%.2f", _globalStats.getAvgTime()));
        outln(buffer, "AVG TIME/GEN (ms): "+String.format("%.3f", _globalStats.getAvgTimePerGeneration()));
        outln(buffer, "TIME MEAN: "+String.format("%.3f", _globalStats.mean.orElse(null)));
        outln(buffer, "TIME STD DEV: "+String.format("%.3f", _globalStats.stdDev.orElse(null)));
        outln(buffer, "TIME MEDIAN: "+String.format("%.3f", _globalStats.median.orElse(null)));
        outln(buffer, "-- Results --");
        outln(buffer, "AVG FITNESS: "+String.format("%.10f", _globalStats.getAvgFitness()));
        outln(buffer, "MAX FITNESS: "+String.format("%.10f", _globalStats._maxFitness.orElse(null)));
        outln(buffer, "MIN FITNESS: "+String.format("%.10f", _globalStats._minFitness.orElse(null)));
        
        return buffer.toString();
    }
    
    private void outln(StringBuffer buffer, String msg){
    	buffer.append(msg).append(System.getProperty("line.separator"));
    }
}
