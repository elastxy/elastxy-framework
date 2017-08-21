package it.red.algen.engine;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public class UniformlyDistributedSelector implements Selector<Genoma> {

	private PopulationFactory populationFactory;
	
    public void setup(AlgorithmParameters algParameters) {
    }
    
    public void setup(AlgorithmParameters algParameters, PopulationFactory populationFactory) {
    	this.populationFactory = populationFactory;
    }

    /** SELECTION
     *  Creates a new random population
     */
    public Population select(Population actualGeneration, Genoma genoma){
    	Population nextGen = populationFactory.createNew(genoma);
        return nextGen;
    }
	    
}
