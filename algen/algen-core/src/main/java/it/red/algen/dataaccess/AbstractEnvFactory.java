/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.dataaccess;

import it.red.algen.context.AlgorithmContext;
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

	protected abstract Target<T,R> defineTarget(Genoma genoma);

	
    public Env create(){
    	
    	// Setup the data algorithm is based on
    	// TODOA: now coupled with genomaprovider: must be separated
//    	WorkingDataset workingDataset = setupWorkingDataset();
    	
    	// Retrieve GenomaProvider
		GenomaProvider genomaProvider = context.application.genomaProvider;
		
    	// Create genoma
    	Genoma genoma = createGenoma(genomaProvider);
    	
    	// Define target
    	// TODOA: remove genoma as parameter of target, use working data set instead
    	// and push target definition above
    	Target<T,R> target = createTarget(genoma); 

    	// Reduce Genoma based on target
    	genoma = reduceGenoma(genomaProvider, target);

//    	// Setup incubator
//    	setupIncubator(genoma);
    	
    	// Create initial population
    	Population startGen = createInitialPopulation(genoma);
        
        // Create environment
        Env env = new Env(target, startGen, genoma);
        
        return env;
    }

	private Genoma createGenoma(GenomaProvider genomaProvider) {
		genomaProvider.collect();
		return genomaProvider.getGenoma();
	}
	
	private Target<T,R> createTarget(Genoma genoma){
		Target<T,R> target = defineTarget(genoma);
		target.setTargetFitness(context.parameters.stopConditions.targetFitness);
    	target.setTargetThreshold(context.parameters.stopConditions.targetThreshold);
    	return target;
	}

	private Genoma reduceGenoma(GenomaProvider genomaProvider, Target<T,R> target) {
		return genomaProvider.shrink(target);
	}

	private Population createInitialPopulation(Genoma genoma) {
		long solutions = 		context.parameters.initialSelectionNumber;
		boolean random = 		context.parameters.initialSelectionRandom;
        Population startGen = 	context.application.populationFactory.createNew(genoma, solutions, random);
		return startGen;
	}

}
