package it.red.algen.engine.factory;

import java.util.List;

import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genoma;

public interface PopulationFactory<G extends Genoma> {

	public Population createNew(G genoma, long solutions, boolean random);
	
	/**
	 * Creates a new Population adding previously calculated best matches.
	 * 
	 * Useful when starting from a previous Era in MultiColony environment.
	 * @param genoma
	 * @param solutions
	 * @param random
	 * @param previousBestMatches
	 * @return
	 */
	public Population createNew(G genoma, long solutions, boolean random, List<Solution> previousBestMatches);
	
	public void setSolutionsFactory(SolutionsFactory factory);
	
}
