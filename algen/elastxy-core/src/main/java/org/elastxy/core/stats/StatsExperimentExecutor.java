/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.stats;

import java.util.Optional;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.engine.core.Experiment;
import org.elastxy.core.engine.core.SingleColonyExperiment;



/**
 * 
 * Collects a number of stats executing a number of experiments.
 *
 * @author grossi
 */
public class StatsExperimentExecutor {
    
	private AlgorithmContext context;
	
    private int experiments;
    
    private AggregatedStats globalStats;
    
    public StatsExperimentExecutor(AlgorithmContext context, int experiments){
        this.context = context;
    	this.experiments = experiments;
        globalStats = new AggregatedStats();
        globalStats.totExperiments = experiments;
    }
    
    private void addStats(ExperimentStats stats){
    	
    	// General stats
        if(stats.targetReached){
        	globalStats.successExecutionTimes.add(stats.executionTimeMs);
        	globalStats.totSuccesses++;
        	globalStats.bestMatch = Optional.of(stats.bestMatch.toString());
        }
        globalStats.bestMatches.add(stats.bestMatch.toString());
        
        // Execution stats
        globalStats.totTime += stats.executionTimeMs;
        globalStats.totGenerations += stats.generations;
        double bestMatchFitness = stats.bestMatch.getFitness().getValue().doubleValue();
        globalStats.totFitness += bestMatchFitness;
        globalStats.maxFitness = Optional.of(globalStats.maxFitness.isPresent() ? Math.max(globalStats.minFitness.get(), bestMatchFitness) : bestMatchFitness);
        globalStats.minFitness = Optional.of(globalStats.minFitness.isPresent() ? Math.min(globalStats.minFitness.get(), bestMatchFitness) : bestMatchFitness);
        globalStats.totBestMatches += stats.lastGeneration.bestMatches==null?0:stats.lastGeneration.bestMatches.size();
    }
    
    public void run(){
        for(int i = 0; i < experiments; i++){
            Experiment e = new SingleColonyExperiment(context);
            e.run();
            addStats(e.getStats());
        }
        
        calculateGlobalStats();
    }
    
    private void calculateGlobalStats(){
    	
    	// Get a DescriptiveStatistics instance
    	DescriptiveStatistics stats = new DescriptiveStatistics();

    	// Add the data from the array
    	for( int i = 0; i < globalStats.successExecutionTimes.size(); i++) {
    	        stats.addValue(globalStats.successExecutionTimes.get(i));
    	}

    	// Compute some statistics
    	globalStats.mean = Optional.of(stats.getMean());
    	globalStats.stdDev = Optional.of(stats.getStandardDeviation());
    	globalStats.median = Optional.of(stats.getPercentile(50.0));
    }
    
    public String print(){
    	StringBuffer buffer = new StringBuffer();
    	
        outln(buffer, "\n\n@@@@@@@@@@@@  GLOBAL STATS @@@@@@@@@@@");
        outln(buffer, "EXAMPLE SOLUTION: "+globalStats.bestMatch.orElse("None"));
        outln(buffer, "EXPERIMENTS: "+globalStats.totExperiments);
        outln(buffer, "SUCCESSES: "+globalStats.getPercSuccesses()+"%");
        
        outln(buffer, "-- Execution Statistics --");
        outln(buffer, "AVG GEN: "+globalStats.getAvgGenerations());
        outln(buffer, "AVG TIME (ms): "+String.format("%.2f", globalStats.getAvgTime()));
        outln(buffer, "AVG TIME/GEN (ms): "+String.format("%.3f", globalStats.getAvgTimePerGeneration()));
        outln(buffer, "TIME MEAN: "+String.format("%.3f", globalStats.mean.orElse(null)));
        outln(buffer, "TIME STD DEV: "+String.format("%.3f", globalStats.stdDev.orElse(null)));
        outln(buffer, "TIME MEDIAN: "+String.format("%.3f", globalStats.median.orElse(null)));
        outln(buffer, "-- Results Statistics --");
        outln(buffer, "AVG FITNESS: "+String.format("%.10f", globalStats.getAvgFitness()));
        outln(buffer, "MAX FITNESS: "+String.format("%.10f", globalStats.maxFitness.orElse(null)));
        outln(buffer, "MIN FITNESS: "+String.format("%.10f", globalStats.minFitness.orElse(null)));
        outln(buffer, "AVG BEST MATCHES: "+globalStats.getAvgBestMatchesNumber());
        outln(buffer, "BEST MATCHES: "+globalStats.bestMatches);
        
        return buffer.toString();
    }
    
    private void outln(StringBuffer buffer, String msg){
    	buffer.append(msg).append(System.getProperty("line.separator"));
    }
}
