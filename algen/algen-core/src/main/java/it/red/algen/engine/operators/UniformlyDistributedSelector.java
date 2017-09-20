package it.red.algen.engine.operators;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.genetics.Genoma;

public class UniformlyDistributedSelector implements Selector<Genoma> {
	private AlgorithmContext context;
	
	public void setup(AlgorithmContext context){
		this.context = context;
    }

    /** SELECTION
     *  Creates a new random population
     */
    public Population select(Population actualGeneration, Genoma genoma){
    	PopulationFactory populationFactory = context.application.populationFactory;
    	Population nextGen = populationFactory.createNew(
    			genoma, 
    			context.algorithmParameters.initialSelectionNumber, 
    			context.algorithmParameters.initialSelectionRandom);
        return nextGen;
    }
	    
}
