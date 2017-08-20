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
     * TODOM: sudoku matrix sent from user, or in external file
     * 
     * @return
     */
    private int[][] createGoal(){
    	
		int[][] matrix = new int[9][9];
		matrix[0] = new int[]{1,0,3,0,8,5,6,2,0};
		matrix[1] = new int[]{0,5,2,1,6,7,8,3,9};
		matrix[2] = new int[]{9,8,6,0,3,4,7,0,1};
		matrix[3] = new int[]{7,2,0,8,4,3,9,6,5};
		matrix[4] = new int[]{3,9,5,6,7,0,0,4,8};
		matrix[5] = new int[]{8,6,0,5,9,0,2,0,3};
		matrix[6] = new int[]{6,4,0,0,2,9,0,1,7};
		matrix[7] = new int[]{2,1,7,4,5,8,3,9,6};
		matrix[8] = new int[]{5,0,9,7,1,0,4,8,0};
		
		return matrix;
    }


	@Override
	protected SolutionsFactory<PredefinedGenoma> getSolutionsFactory() {
		return solutionsFactory;
	}


}
