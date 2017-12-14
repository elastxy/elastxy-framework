package org.elastxy.core.engine.factory;

import java.util.List;

import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;

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
