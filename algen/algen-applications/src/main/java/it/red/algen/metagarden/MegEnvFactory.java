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
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.StandardMetadataGenoma;

/**
 *
 * @author grossi
 */
@Component
public class MegEnvFactory extends AbstractEnvFactory<String, Double, StandardMetadataGenoma> {
	
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private MegSolutionsFactory solutionsFactory;
	
	@Autowired private MegGenomaProvider genomaProvider;
	
	@Override
	protected PerformanceTarget<String, Double> defineTarget(Genoma genoma) {
		PerformanceTarget<String,Double> gardenTarget = new PerformanceTarget<String,Double>();
    	gardenTarget.setGoal(contextSupplier.getContext().applicationSpecifics.getTargetString(MegApplication.TARGET_WELLNESS));
    	gardenTarget.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);
    	// Determines goal rough measure: minimum possible unhappiness (illness), 0.0
    	gardenTarget.setReferenceMeasure(genoma.getPositionsSize() * 2.0);  // 2 is the maximum value happiness can reach
		return gardenTarget;
	}

	@Override
	protected GenomaProvider getGenomaProvider() {
		return genomaProvider;
	}

	@Override
	protected SolutionsFactory<StandardMetadataGenoma> getSolutionsFactory() {
		return solutionsFactory;
	}


}
