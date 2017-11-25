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

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.dataprovider.DistributedDatasetProvider;
import it.red.algen.distributed.dataprovider.DistributedGenomaProvider;
import it.red.algen.distributed.experiment.MultiColonyEnv;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;

/**
 * 
 * Env Factory for creating a MultiColonyEnv or evolving it through Eras
 *
 * @author grossi
 */
public abstract class MultiColonyAbstractEnvFactory<T extends Object, R extends Object, G extends Genoma> implements MultiColonyEnvFactory {
    
	protected AlgorithmContext context;
	
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	protected abstract Target<T,R> defineTarget(WorkingDataset dataset);

	@Override
	public MultiColonyEnv createEnv(){

    	// Collect the data algorithm is based on
    	DistributedDatasetProvider datasetProvider = (DistributedDatasetProvider)context.application.datasetProvider;
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
		DistributedGenomaProvider genomaProvider = (DistributedGenomaProvider)context.application.distributedGenomaProvider;
    	genomaProvider.setWorkingDataset(workingDataset);
				
    	// Create genoma
    	Genoma genoma = createGenoma(genomaProvider);

    	// Reduce Genoma based on target
    	genoma = reduceGenoma(genomaProvider, target);

        // Create environment
        MultiColonyEnv env = new MultiColonyEnv(target, genoma);
        
        return env;
	}


	private Genoma createGenoma(GenomaProvider genomaProvider) {
		genomaProvider.collect();
		return genomaProvider.getGenoma();
	}
	
	private Target<T,R> createTarget(WorkingDataset workingDataset){
		Target<T,R> target = defineTarget(workingDataset);
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
    	DistributedDatasetProvider datasetProvider = (DistributedDatasetProvider)context.application.datasetProvider;
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

    
}
