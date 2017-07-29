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
    public int _totExperiments;
    public int _totSuccesses;
    public int _totGenerations;
    public double _totTime;
    public double _totFitness;
    public Optional<Double> _minFitness = Optional.empty();
    public Optional<Double> _maxFitness = Optional.empty();
    
    public Optional<Double> mean = Optional.empty();
    public Optional<Double> stdDev = Optional.empty();
    public Optional<Double> median = Optional.empty();
    
    public transient List<Double> successExecutionTimes = new ArrayList<Double>();
    
    public AggregatedStats() {
    }
    
    public double getPercSuccesses(){
        return _totSuccesses / (double)_totExperiments * 100.0;
    }
    public double getAvgGenerations(){
        return _totGenerations / (double)_totExperiments;
    }
    public double getAvgTime(){
        return _totTime / (double)_totExperiments;
    }
    public double getAvgTimePerGeneration(){
        return _totTime / (double)_totGenerations;
    }
    public double getAvgFitness(){
        return _totFitness / (double)_totExperiments;
    }
    
}
