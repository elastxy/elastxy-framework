/*
 * GlobalStats.java
 *
 * Created on 5 agosto 2007, 14.59
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author grossi
 */
public class AggregatedStats {
	
	// Experiment stats
	public int totExperiments;
    public int totSuccesses;

    // Execution stats
    public int totGenerations;
    public double totTime;
    public double totFitness;

    public Optional<Double> mean = Optional.empty();
    public Optional<Double> stdDev = Optional.empty();
    public Optional<Double> median = Optional.empty();
    
	// Experiment solutions
    public Optional<Double> minFitness = Optional.empty();
    public Optional<Double> maxFitness = Optional.empty();
    
	public Optional<String> bestMatch = Optional.empty();
	public List<String> bestMatches = new ArrayList<String>();
	public long totBestMatches;

	
    public transient List<Long> successExecutionTimes = new ArrayList<Long>();
    
    public AggregatedStats() {
    }
    
    public double getPercSuccesses(){
        return totSuccesses / (double)totExperiments * 100.0;
    }
    public double getAvgGenerations(){
        return totGenerations / (double)totExperiments;
    }
    public double getAvgTime(){
        return totTime / (double)totExperiments;
    }
    public double getAvgTimePerGeneration(){
        return totTime / (double)totGenerations;
    }
    public double getAvgFitness(){
        return totFitness / (double)totExperiments;
    }
    public double getAvgBestMatchesNumber(){
        return totBestMatches / (double)totGenerations;
    }
    
    
    public String toString(){
    	return String.format("AggrStats: successes %d of experiments %d, fitness: max %.20f, avg %.20f, BestMatch: %s", 
    			totSuccesses, 
    			totExperiments,
    			maxFitness,
    			getAvgFitness(),
    			bestMatch);
    }
}
