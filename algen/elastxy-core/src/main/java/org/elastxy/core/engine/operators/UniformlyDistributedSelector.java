package org.elastxy.core.engine.operators;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.engine.factory.PopulationFactory;

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
