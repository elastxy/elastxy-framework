package it.red.algen.engine.fitness;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.phenotype.Phenotype;
import it.red.algen.engine.core.IllegalSolutionException;

public interface Incubator<G extends Genotype, P extends Phenotype> {

	/**
	 * Create the phenotype starting from the genotype.
	 * 
	 *  If needed, the surrounding environment can be provided to give Incubator
	 *  further data for growing individuals.
	 *  
	 *  E.g. in Mef WorkingDataset is given to get meaningful data from original recipes.
	 *  
	 *  TODOM-4: EnvironmentAware interface for injecting Env runtime context without passing in signatures
	 * @param genotype
	 * @return
	 */
	public P grow(G genotype, Env environment) throws IllegalSolutionException;
	
}
