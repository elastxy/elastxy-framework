package it.red.algen.engine.factory;

import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public interface PopulationFactory<G extends Genoma> {

	public Population createNew(G genoma, long solutions, boolean random);
	
	public void setSolutionsFactory(SolutionsFactory factory);
	
}
