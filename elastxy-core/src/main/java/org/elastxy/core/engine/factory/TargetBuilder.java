package org.elastxy.core.engine.factory;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;

/*
 * Creates a target for a new environment.
 */
public interface TargetBuilder<G,M> {

	public void setup(AlgorithmContext context);
	
	public Target<G,M> define(WorkingDataset workingDataset);

}
