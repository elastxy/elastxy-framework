/*
 * GlobalStats.java
 *
 * Created on 5 agosto 2007, 14.59
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author grossi
 */
public class AggregatedStats {
    public int totExperiments;
    public int totSuccesses;
    public int totGenerations;
    public double totTime;
    public double totFitness;
    public Optional<Double> minFitness = Optional.empty();
    public Optional<Double> maxFitness = Optional.empty();
    
    public Optional<Double> mean = Optional.empty();
    public Optional<Double> stdDev = Optional.empty();
    public Optional<Double> median = Optional.empty();
    
    public transient List<Double> successExecutionTimes = new ArrayList<Double>();
    
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
    
}
