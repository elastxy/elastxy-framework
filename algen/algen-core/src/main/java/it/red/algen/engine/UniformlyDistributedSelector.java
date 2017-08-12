package it.red.algen.engine;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;

public class UniformlyDistributedSelector implements Selector {

	private PopulationFactory populationFactory;
	
    public void setup(OperatorsParameters algParameters) {
    }
    
    public void setup(OperatorsParameters algParameters, PopulationFactory populationFactory) {
    	this.populationFactory = populationFactory;
    }

    /** SELECTION
     *  Creates a new random population
     */
    public Population select(Population actualGeneration){
    	Population nextGen = populationFactory.createNew();
        return nextGen;
    }
	    
}
