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

import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.engine.metadata.StandardMetadataGenoma;

/**
 *
 * @author grossi
 */
public class MegEnvFactory extends AbstractEnvFactory<String, Double, StandardMetadataGenoma> {
	
	@Override
	protected PerformanceTarget<String, Double> defineTarget(Genoma genoma) {
		PerformanceTarget<String,Double> target = new PerformanceTarget<String,Double>();
    	target.setGoal(context.applicationSpecifics.getTargetString(MegConstants.TARGET_WELLNESS));
    	// Determines goal rough measure: minimum possible unhappiness (illness), 0.0
    	target.setReferenceMeasure(genoma.getGenotypeStructure().getPositionsSize() * 2.0);  // 2 is the maximum value happiness can reach
		return target;
	}

}
