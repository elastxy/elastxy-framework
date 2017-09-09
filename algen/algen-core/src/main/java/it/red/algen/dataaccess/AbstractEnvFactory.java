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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;

/**
 *
 * @author grossi
 */
@Component
public abstract class AbstractEnvFactory<T extends Object, R extends Object, G extends Genoma> implements EnvFactory {
	
	@Autowired private PopulationFactory populationFactory;
	
    public Env create(){
    	
    	// Setup the data algorithm is based on
    	// TODOA: now coupled with genomaprovider: must be separated
//    	WorkingDataset workingDataset = setupWorkingDataset();
    	
    	// Retrieve GenomaProvider
		GenomaProvider genomaProvider = getGenomaProvider();
		
    	// Create genoma
    	Genoma genoma = createGenoma(genomaProvider);
    	
    	// Define target
    	// TODOA: remove genoma as parameter of target, use working data set instead
    	// and push target definition above
    	Target<T,R> target = defineTarget(genoma); 

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

	private Genoma reduceGenoma(GenomaProvider genomaProvider, Target<T,R> target) {
		return genomaProvider.reduce(target);
	}

	private Population createInitialPopulation(Genoma genoma) {
		populationFactory.setSolutionsFactory(getSolutionsFactory());
        Population startGen = populationFactory.createNew(genoma);
		return startGen;
	}

	protected abstract GenomaProvider getGenomaProvider();
	
//	/**
//	 * Optional
//	 */
//	protected void setupIncubator(Genoma genoma){}

	protected abstract Target<T,R> defineTarget(Genoma genoma);

	protected abstract SolutionsFactory<G> getSolutionsFactory();

}
