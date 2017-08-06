package it.red.algen.expressions.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.domain.interfaces.Solution;
import it.red.algen.engine.interfaces.SolutionsFactory;
import it.red.algen.expressions.domain.ExprSolution;

@Component
public class ExprSolutionFactory implements SolutionsFactory {

    @Autowired
    private ExprGenesFactory genesFactory;

    
    public Solution createRandom() {
    	return new ExprSolution(
    			genesFactory.getRandomNumber(),
    			genesFactory.getRandomOperator(),
    			genesFactory.getRandomNumber());
    }
    
    public Solution createBaseModel() {
    	return new ExprSolution(
			genesFactory.getNumber(0),
			genesFactory.getOperator('+'),
			genesFactory.getNumber(0));
    }
    
    

    // TODOM: representation change only: to be managed in a Transformer...
    public ExprSolution create(int val1, char op,int val2) {
    	return new ExprSolution(
    			genesFactory.getNumber(val1),
    			genesFactory.getOperator(op),
    			genesFactory.getNumber(val2));
    }
    
}
