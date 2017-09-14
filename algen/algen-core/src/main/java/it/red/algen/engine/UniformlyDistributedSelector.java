package it.red.algen.engine;

import it.red.algen.conf.AlgorithmParameters;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public class UniformlyDistributedSelector implements Selector<Genoma> {
	private AlgorithmParameters parameters;
	private PopulationFactory populationFactory;
	
    public void setup(AlgorithmParameters parameters) {
    	this.parameters = parameters;
    }
    
    public void setup(AlgorithmParameters algParameters, PopulationFactory populationFactory) {
    	this.populationFactory = populationFactory;
    }

    /** SELECTION
     *  Creates a new random population
     */
    public Population select(Population actualGeneration, Genoma genoma){
    	Population nextGen = populationFactory.createNew(genoma, parameters.initialSelectionNumber, parameters.initialSelectionRandom);
        return nextGen;
    }
	    
}
