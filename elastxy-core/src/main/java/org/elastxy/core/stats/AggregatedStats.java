/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
