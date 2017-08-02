package it.red.algen.expressions.engine;

import java.util.Random;

import it.red.algen.domain.Solution;
import it.red.algen.engine.GenesFactory;
import it.red.algen.engine.Mutator;
import it.red.algen.expressions.domain.ExprSolution;

public class ExprMutator implements Mutator {
    private static Random RANDOMIZER = new Random();

    private ExprGenesFactory genesFactory;
    
	public void setGenesFactory(GenesFactory genesFactory){
		this.genesFactory = (ExprGenesFactory)genesFactory;
	}

    
    /** Piu' probabile la mutazione dell'operatore
     */
	public Solution mutate(Solution solution) {
		ExprSolution result = (ExprSolution)solution;
        switch(RANDOMIZER.nextInt(4)){
        case 0:
        	result.val1 = genesFactory.getRandomNumber();
            break;
        case 1:
        case 2:
        	result.op =  genesFactory.getRandomOperator();
            break;
        case 3:
        	result.val2 =  genesFactory.getRandomNumber();
            break;
        }
        return result;
	}

}
