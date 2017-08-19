/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.metagarden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

/**
 *
 * @author grossi
 */
@Component
public class MegEnvFactory implements EnvFactory {
	
	
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private PopulationFactory populationFactory;
	
	@Autowired private MegSolutionsFactory solutionsFactory;
	
	@Autowired private MegGenomaProvider genomaProvider;
	
    public Env create(){

    	// Create genoma
    	Genoma genoma = createGenoma();
    	
    	// Create initial population
    	Population startGen = createInitialPopulation(genoma);
    	
    	// Define target
    	PerformanceTarget<String, Double> target = defineTarget(startGen);

        // Create environment
        Env env = new Env(target, startGen, genoma);
        
        return env;
    }


	private Genoma createGenoma() {
//    	genomaProvider.reduce(target);
		genomaProvider.collect();
		return genomaProvider.getGenoma();
	}
	
	private PerformanceTarget<String, Double> defineTarget(Population startGen) {
		PerformanceTarget<String,Double> gardenTarget = new PerformanceTarget<String,Double>();
    	gardenTarget.setGoal(contextSupplier.getContext().applicationSpecifics.getTargetString(MegApplication.TARGET_WELLNESS));
    	gardenTarget.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);
    	// Determines goal rough measure: minimum possible unhappiness (illness), 0.0
    	gardenTarget.setReferenceMeasure(startGen.solutions.get(0).getGenotype().getPositions().size() * 2.0);  // 2 is the maximum value happiness can reach
		return gardenTarget;
	}


	private Population createInitialPopulation(Genoma genoma) {
		populationFactory.setSolutionsFactory(solutionsFactory);
        Population startGen = populationFactory.createNew(genoma);
		return startGen;
	}


}
