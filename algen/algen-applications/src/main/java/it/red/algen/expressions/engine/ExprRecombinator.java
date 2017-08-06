package it.red.algen.expressions.engine;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import it.red.algen.engine.interfaces.Recombinator;
import it.red.algen.expressions.domain.ExprSolution;

public class ExprRecombinator implements Recombinator<ExprSolution> {
    private static Random RANDOMIZER = new Random();

    /**
     * I punti di ricombinazione possono essere all'operatore o al secondo operando
     * Two are the expected parents
     * 
     * TODOB: multiple parents management
     * 
     * @param other
     * @return
     */
	public List<ExprSolution> recombine(List<ExprSolution> parents){
		ExprSolution[] sons = new ExprSolution[2];
        int crossoverPoint = RANDOMIZER.nextInt(2);
        ExprSolution parent1 = parents.get(0);
        ExprSolution parent2 = parents.get(1);
        if(crossoverPoint==0){
            sons[0] = new ExprSolution(parent2.val1,	parent1.op,	parent1.val2);
            sons[1] = new ExprSolution(parent1.val1,	parent2.op,	parent2.val2);
        } else {
            sons[0] = new ExprSolution(parent2.val1,	parent2.op,	parent1.val2);
            sons[1] = new ExprSolution(parent1.val1,	parent1.op,	parent2.val2);
        }
        
        return Arrays.asList(sons);
    }
	
}
