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
package org.elastxy.core.engine.core;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObserver;




/**
 *	Local (single colony) experiment.
 *
 *	Defines execution for a locally based algorithm:
 *  - local events
 *  - local populations and factory
 *  - local evolver
 *  
 *  No distributed nor concurrent features are available 
 *  within this Experiment type.
 *  
 *  In distributed MultiColony context, the SingleColonyExperiment
 *  runs a big number of generations from an initial population, into an Era 
 *  within a closure and based on Genoma provided by a distributed broadcast 
 *  set for mutation and a rdd iterator for initial population.
 *
 * @author grossi
 */
public class SingleColonyExperiment implements Experiment {
    private ExperimentStats stats;

    private AlgorithmContext context;
    
    public SingleColonyExperiment(AlgorithmContext context) {
        this.context = context;
        stats = null;
    }
    
    public ExperimentStats getStats(){
        return stats;
    }
    
    public void run(){
    	
    	// Setups observer
        EnvObserver observer = new EnvObserver(context);
        
        // Creates initial environment
        Env environment = context.application.envFactory.create();
    	
        // Setups engine
        SingleColonyEvolver evolver = new SingleColonyEvolver(
        		context, 
        		environment);
        evolver.subscribe(observer);
        
        // Starts evolution
        evolver.evolve();
        
        // Retrieves stats
        stats = evolver.getStats();
    }
    
    public String toString(){
    	return String.format("Experiment stats: %s", stats);
    }
}
