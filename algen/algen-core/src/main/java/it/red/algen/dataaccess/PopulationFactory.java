package it.red.algen.dataaccess;

import it.red.algen.domain.experiment.Population;

public interface PopulationFactory {

	public Population createNew();
	
	// TODOA: manage by autowiring
	public void setSolutionsFactory(SolutionsFactory factory);
	
}
