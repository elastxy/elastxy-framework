package it.red.algen.engine.standard;

import it.red.algen.conf.OperatorsParameters;
import it.red.algen.domain.Population;
import it.red.algen.engine.Selector;
import it.red.algen.engine.interfaces.PopulationFactory;

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
