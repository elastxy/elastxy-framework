package it.red.algen.engine;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

public interface Incubator<G extends Genotype, P extends Phenotype> {

	/**
	 * Create the phenotype starting from the genotype.
	 * 
	 *  If needed, the surrounding environment can be provided.
	 *  
	 *  TODOA: create an interface EnvironmentAware for injecting Env runtime context
	 * @param genotype
	 * @return
	 */
	public P grow(G genotype, Env environment);
	
}
