package it.red.algen.dataaccess;

import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public interface PopulationFactory<G extends Genoma> {

	public Population createNew(G genoma);
	
	// TODOA: manage by autowiring
	public void setSolutionsFactory(SolutionsFactory factory);
	
}
