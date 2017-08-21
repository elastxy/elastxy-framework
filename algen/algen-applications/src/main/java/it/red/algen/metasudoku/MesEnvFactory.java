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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.PredefinedGenoma;

/**
 * TODOA: remove duplications with other factories
 *
 * @author grossi
 */
@Component
public class MesEnvFactory extends AbstractEnvFactory<int[][], Integer, PredefinedGenoma> {
	private Logger logger = Logger.getLogger(MesEnvFactory.class);
	
	
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private MesSolutionFactory solutionsFactory;

	@Autowired private MesGenomaProvider genomaProvider;
	


	@Override
	protected GenomaProvider getGenomaProvider() {
		return genomaProvider;
	}
	
	
	@Override
	protected Target<int[][], Integer> defineTarget(Genoma genoma) {
		// Define evolution environment
    	AlgorithmContext context = contextSupplier.getContext();
    	
    	// Defines goal representation
    	PerformanceTarget target = new PerformanceTarget();
    	target.setGoal(createGoal());
    	target.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);

    	// Determines goal rough measure by deriving from extreme solutions
    	// 27 is the number of rows, columns, squares with numbers 1 to 9
    	target.setReferenceMeasure(MesApplication.TOTAL_COMPLETED);
		return target;
	}

    
    /**
     * Simple Sudoku matrix
     * 
     * @return
     */
    private int[][] createGoal(){
		String classpathResource = "/"+MesApplication.APP_NAME+"/target.json";
		return (int[][])ReadConfigSupport.readJSON(classpathResource, int[][].class);
    }


	@Override
	protected SolutionsFactory<PredefinedGenoma> getSolutionsFactory() {
		return solutionsFactory;
	}


}
