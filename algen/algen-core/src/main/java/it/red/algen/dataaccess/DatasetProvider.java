package it.red.algen.dataaccess;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Target;

public interface DatasetProvider {

	public void setup(AlgorithmContext context);

	public WorkingDataset getWorkingDataset();
	
	public void collect();
	
	public void shrink(Target<?, ?> target);
	
}
