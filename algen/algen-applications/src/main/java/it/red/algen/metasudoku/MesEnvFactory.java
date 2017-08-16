/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.metasudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Population;

/**
 * TODOA: remove duplications with other factories
 *
 * @author grossi
 */
@Component
public class MesEnvFactory implements EnvFactory {
	
	
	@Autowired
	private PopulationFactory populationFactory;
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private MesSolutionFactory solutionsFactory;
	
	
    public Env create(){
        
    	// Create initial population
    	populationFactory.setSolutionsFactory(solutionsFactory);
        Population startGen = populationFactory.createNew();
        
        // Define evolution environment
        AlgorithmContext context = contextSupplier.getContext();

        // Defines goal representation
        PerformanceTarget target = new PerformanceTarget();
        target.setGoal("win");
        target.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);

        // Determines goal rough measure by deriving from extreme solutions
        // 27 is the number of rows, columns, squares with numbers 1 to 9
        target.setReferenceMeasure(MesApplication.TOTAL_COMPLETED);
        
        // Creates environment
        Env env = new Env(target, startGen);
        
        return env;
    }


}
