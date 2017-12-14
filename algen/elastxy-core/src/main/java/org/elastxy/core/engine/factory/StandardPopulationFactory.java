/*
 * PopulationFactory.java
 *
 * Created on 4 agosto 2007, 14.08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.engine.factory;

import java.util.List;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.Genoma;

/**
 *
 * @author grossi
 */
public class StandardPopulationFactory implements PopulationFactory<Genoma> {
	private static Logger logger = Logger.getLogger(StandardPopulationFactory.class);
	
	private SolutionsFactory solutionsFactory;
	
	@Override
	public void setSolutionsFactory(SolutionsFactory solutionsFactory) {
		this.solutionsFactory = solutionsFactory;
	}

	@Override
    public Population createNew(Genoma genoma, long solutions, boolean random) {
        Population population = new Population();
        for(int i = 0; i < solutions; i++){
        	if(random){
                population.add(solutionsFactory.createRandom(genoma));
        	}
        	else {
        		population.add(solutionsFactory.createBaseModel(genoma));
        	}
        }
        return population;
    }

	@Override
	public Population createNew(Genoma genoma, long solutions, boolean random, List<Solution> preservedSolutions) {
		int bmNumber = preservedSolutions==null || preservedSolutions.isEmpty() ? 0 : preservedSolutions.size();
		if(bmNumber > solutions){
			if(logger.isInfoEnabled()) logger.info("Cannot reinsert previous "+bmNumber+" best matches to a population of "+solutions+" solutions. Only one best match will be readded.");
			bmNumber = 1;
		}
		
		// Creates a population leaving blank solutions 
		Population population = createNew(genoma, solutions-bmNumber, random);
		
		// Insert previous best matches into new population
		if(preservedSolutions!=null) for(Solution s : preservedSolutions) { population.add(s); }
        return population;
	}

}
