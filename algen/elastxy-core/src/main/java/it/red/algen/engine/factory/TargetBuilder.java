package it.red.algen.engine.factory;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Target;

/*
 * Creates a target for a new environment.
 */
public interface TargetBuilder<G,M> {

	public void setup(AlgorithmContext context);
	
	public Target<G,M> define(WorkingDataset workingDataset);

}
