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
package org.elastxy.core.engine.factory;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.DatasetProvider;
import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;

/**
 *
 * @author grossi
 */
public class StandardEnvFactory<T extends Object, R extends Object, G extends Genoma> implements EnvFactory {
	private AlgorithmContext context;
	private TargetBuilder targetBuilder;
	
	@Override
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	@Override
	public void setTargetBuilder(TargetBuilder targetBuilder){
		this.targetBuilder = targetBuilder;
	}
	
    public Env create(){
    	
    	// Collect the data algorithm is based on
    	DatasetProvider datasetProvider = context.application.datasetProvider;
    	WorkingDataset workingDataset = null;
    	if(datasetProvider!=null){
    		datasetProvider.collect();
    		workingDataset = datasetProvider.getWorkingDataset();
    	}
    	
    	// Define target
    	Target<T,R> target = createTarget(workingDataset); 

    	// Reduce initial data based on target
    	if(datasetProvider!=null){
    		datasetProvider.shrink(target);
    		workingDataset = datasetProvider.getWorkingDataset();
    	}
    	
    	// Retrieve GenomaProvider
		GenomaProvider genomaProvider = createGenomaProvider(workingDataset);
				
    	// Create genoma
    	Genoma genoma = createGenoma(genomaProvider);

    	// Reduce Genoma based on target
    	genoma = reduceGenoma(genomaProvider, target);

//    	// Setup incubator
//    	setupIncubator(genoma);
    	
    	// Create initial population
    	Population startGen = createInitialPopulation(genoma);
        
        // Create environment
        Env env = new Env(target, startGen, genoma, workingDataset);
        
        return env;
    }
    
    
    private GenomaProvider createGenomaProvider(WorkingDataset workingDataset){
    	GenomaProvider genomaProvider = context.application.genomaProvider;
    	genomaProvider.setWorkingDataset(workingDataset);
    	return genomaProvider;
    }

	private Genoma createGenoma(GenomaProvider genomaProvider) {
		genomaProvider.collect();
		return genomaProvider.getGenoma();
	}
	
	private Target<T,R> createTarget(WorkingDataset workingDataset){
		Target<T,R> target = targetBuilder.define(workingDataset);
		target.setTargetFitness(context.algorithmParameters.stopConditions.targetFitness);
    	target.setTargetThreshold(context.algorithmParameters.stopConditions.targetThreshold);
    	return target;
	}

	protected Genoma reduceGenoma(GenomaProvider genomaProvider, Target<T,R> target) {
		return genomaProvider.shrink(target);
	}

	private Population createInitialPopulation(Genoma genoma) {
		long solutions = 		context.algorithmParameters.initialSelectionNumber;
		boolean random = 		context.algorithmParameters.initialSelectionRandom;
        Population startGen = 	context.application.populationFactory.createNew(genoma, solutions, random);
		return startGen;
	}

}
