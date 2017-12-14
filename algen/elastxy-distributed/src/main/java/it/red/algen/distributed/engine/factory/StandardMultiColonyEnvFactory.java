/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.distributed.engine.factory;

import java.util.Map;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.dataprovider.BroadcastWorkingDataset;
import it.red.algen.distributed.dataprovider.DistributedDatasetProvider;
import it.red.algen.distributed.dataprovider.DistributedGenomaProvider;
import it.red.algen.distributed.experiment.MultiColonyEnv;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.engine.factory.TargetBuilder;

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
		
		// TODOM-2: evaluate a specific target builder in distributed environment to set overall goals?
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
	 * TODOM-2: evaluate a specific target builder in distributed environment to set overall goals?
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
    public MultiColonyEnv newEra(MultiColonyEnv env){
    	
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

        return env;
    }


	@Override
	public Env create() {
		throw new UnsupportedOperationException("Use createEnv instead.");
	}

    
}
