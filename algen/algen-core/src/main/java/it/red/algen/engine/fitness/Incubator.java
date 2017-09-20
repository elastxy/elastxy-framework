package it.red.algen.engine.fitness;

import it.red.algen.dataaccess.WorkingDataset;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

public interface Incubator<G extends Genotype, P extends Phenotype> {

	/**
	 * Create the phenotype starting from the genotype.
	 * 
	 *  If needed, the surrounding environment can be provided to give Incubator
	 *  further data for growing individuals.
	 *  
	 *  E.g. in Mef WorkingDataset is given to get meaningful data from original recipes.
	 *  
	 *  TODOM: create an interface EnvironmentAware for injecting Env runtime context without passing in signatures
	 * @param genotype
	 * @return
	 */
	public P grow(WorkingDataset workingDataset, G genotype, Env environment);
	
}
