package org.elastxy.core.dataprovider;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Target;

public interface DatasetProvider {

	public void setup(AlgorithmContext context);

	public WorkingDataset getWorkingDataset();
	
	public void collect();
	
	public void shrink(Target<?, ?> target);
	
}
