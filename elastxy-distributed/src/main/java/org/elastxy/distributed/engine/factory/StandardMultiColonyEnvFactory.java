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
package org.elastxy.distributed.engine.factory;

import java.util.Map;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.engine.factory.TargetBuilder;
import org.elastxy.distributed.dataprovider.BroadcastWorkingDataset;
import org.elastxy.distributed.dataprovider.DistributedDatasetProvider;
import org.elastxy.distributed.dataprovider.DistributedGenomaProvider;
import org.elastxy.distributed.experiment.MultiColonyEnv;

/**
 * 
 * Env Factory for creating a MultiColonyEnv or evolving it through Eras.
 * 
 * It can reuse local components for determining global parameters, like Target.
 * E.g it can reuse MefWorkingDataset to determine Target locally, useful
 * to restrict (shrink) data to be broadcasted to colonies.
 *
 * Types of Dataset to be used:
 * - distributedDatasetProvider & distributedWorkingDataset 	=> used on Driver to spread data and map partitions
 * - singleColonyDatasetProvider & distributedWorkingDataset 	=> used on Closure to read broadcast data
 * - (local)datasetProvider & workingDataset					=> used on Driver to prepare data for shrink, 
 * 																   and on Closure eventually to process data locally
 *
 * @author grossi
 */
public class StandardMultiColonyEnvFactory<T extends Object, R extends Object, G extends Genoma> implements MultiColonyEnvFactory {
	private AlgorithmContext context;
	private TargetBuilder<T,R> targetBuilder;
	
	@Override
	public void setup(AlgorithmContext context){
		this.context = context;
	}

	@Override
	public void setTargetBuilder(TargetBuilder targetBuilder) {
		this.targetBuilder = targetBuilder;
	}

	@Override
	public MultiColonyEnv createEnv(){

		// First, it searches for local env context on Driver to be created,
		// in order to help distributed context to work properly
		Target<T,R> target = null;
		WorkingDataset localWorkingDataset = null;
		if(context.application.datasetProvider!=null){
			context.application.datasetProvider.collect();
			localWorkingDataset = context.application.datasetProvider.getWorkingDataset();
		}
		
		// TODO3-2: evaluate a specific target builder in distributed environment to set overall goals?
		target = createTarget(localWorkingDataset);
			
		if(context.application.datasetProvider!=null){
				context.application.datasetProvider.shrink(target);
		}
		
		
    	// Collect the data algorithm is based on
    	DistributedDatasetProvider distributedDatasetProvider = (DistributedDatasetProvider)context.application.distributedDatasetProvider;
    	WorkingDataset distributedWorkingDataset = null;
    	Map<String, BroadcastWorkingDataset> broadcasDatasets = null;
    	
    	// Collect working data for distributed genoma
    	distributedDatasetProvider.collect();
    	distributedWorkingDataset = distributedDatasetProvider.getWorkingDataset();
    	
    	// Reduce initial data based on target
    	if(target!=null) {
    		distributedDatasetProvider.shrink(target);
    	}
    	distributedWorkingDataset = distributedDatasetProvider.getWorkingDataset();
		
    	// Broadcast data for common data on single colonies
    	distributedDatasetProvider.broadcast();
    	broadcasDatasets = distributedDatasetProvider.getBroadcastDatasets();

    	// Retrieve GenomaProvider
		DistributedGenomaProvider genomaProvider = (DistributedGenomaProvider)context.application.distributedGenomaProvider;
    	genomaProvider.setWorkingDataset(distributedWorkingDataset);
				
    	// Create genoma
    	Genoma genoma = createGenoma(genomaProvider);

//    	// Reduce Genoma based on target
//    	genoma = reduceGenoma(genomaProvider, target);

        // Create environment
        MultiColonyEnv env = new MultiColonyEnv(target, genoma, broadcasDatasets);
        return env;
	}


	private Genoma createGenoma(GenomaProvider genomaProvider) {
		genomaProvider.collect();
		return genomaProvider.getGenoma();
	}
	
	
	/**
	 * In case of MultiColonyEnvFactory, TargetBuilder accepts
	 * the RDDDistributedWorkingDataset, which is not useful
	 * in the Driver by now, only locally.
	 * 
	 * E.g. MegWorkingDataset cannot be casted to RDDDistributedDataset...
	 * 
	 * Logics should be removed, or if found useful maintained
	 * declaring a distributedTargetBuilder component...
	 * 
	 * TODO3-2: evaluate a specific target builder in distributed environment to set overall goals?
	 */
	private Target<T,R> createTarget(WorkingDataset workingDataset){
		Target<T,R> target = targetBuilder.define(workingDataset);
		target.setTargetFitness(context.algorithmParameters.stopConditions.targetFitness);
    	target.setTargetThreshold(context.algorithmParameters.stopConditions.targetThreshold);
    	return target;
	}

	protected Genoma reduceGenoma(GenomaProvider genomaProvider, Target<T,R> target) {
		return genomaProvider.shrink(target);
	}

	
    /**
     * Updates Environment upgrading to a new Era, redistributing original data.
     * 
     * @return
     */
	@Override
    public void newEon(MultiColonyEnv env){
    	
    	// With new data identical fitness check can be misleading
    	env.totIdenticalFitnesses=0; 
    	
    	// Redistributed the data algorithm is based on
    	WorkingDataset workingDataset = null;
    	DistributedDatasetProvider datasetProvider = (DistributedDatasetProvider)context.application.distributedDatasetProvider;
    	if(datasetProvider!=null){
    		datasetProvider.redistribute();
    		workingDataset = datasetProvider.getWorkingDataset();
    	}
    	
    	// Reduce initial data based on target
    	if(datasetProvider!=null){
    		datasetProvider.shrink(env.target);
    		workingDataset = datasetProvider.getWorkingDataset();
    	}
    	
    	// Redistribute (spread) genoma based on distributed data set
		DistributedGenomaProvider genomaProvider = (DistributedGenomaProvider)context.application.distributedGenomaProvider;
		genomaProvider.setWorkingDataset(workingDataset);
		genomaProvider.spread();
    	env.genoma = genomaProvider.getGenoma();

    	// Reduce Genoma based on target
    	env.genoma = genomaProvider.shrink(env.target);
    	
    	// Increment Eon number
    	env.currentEonNumber++;
    }


	@Override
	public Env create() {
		throw new UnsupportedOperationException("Use createEnv instead.");
	}

    
}
