package it.red.algen.engine;

import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

public interface Incubator<G extends Genotype, P extends Phenotype> {

	/**
	 * Create the phenotype starting from the genotype.
	 * @param genotype
	 * @return
	 */
	public P grow(G genotype);
	
}
