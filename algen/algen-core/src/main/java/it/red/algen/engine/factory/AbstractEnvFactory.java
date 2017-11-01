/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.engine.factory;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.DatasetProvider;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;

/**
 *
 * @author grossi
 */
public abstract class AbstractEnvFactory<T extends Object, R extends Object, G extends Genoma> implements EnvFactory {
	
	protected AlgorithmContext context;
	
	public void setup(AlgorithmContext context){
		this.context = context;
	}

	protected abstract Target<T,R> defineTarget(WorkingDataset genoma);

	
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
		Target<T,R> target = defineTarget(workingDataset);
		target.setTargetFitness(context.algorithmParameters.stopConditions.targetFitness);
    	target.setTargetThreshold(context.algorithmParameters.stopConditions.targetThreshold);
    	return target;
	}

	private Genoma reduceGenoma(GenomaProvider genomaProvider, Target<T,R> target) {
		return genomaProvider.shrink(target);
	}

	private Population createInitialPopulation(Genoma genoma) {
		long solutions = 		context.algorithmParameters.initialSelectionNumber;
		boolean random = 		context.algorithmParameters.initialSelectionRandom;
        Population startGen = 	context.application.populationFactory.createNew(genoma, solutions, random);
		return startGen;
	}

}
