/*
 * ExprSolution.java
 *
 * Created on 4 agosto 2007, 14.40
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions.domain;

import java.util.Random;

import it.red.algen.domain.Fitness;
import it.red.algen.domain.Solution;

/**
 *
 * @author grossi
 */
public class ExprSolution implements Solution {
    private static Random RANDOMIZER = new Random();
    
	// Phenotype
	public NumberGene val1;
    public OperatorGene op;
    public NumberGene val2;

    // Runtime data
    private Fitness fitness;

    
    public ExprSolution(NumberGene val1, OperatorGene op, NumberGene val2) {
    	this.val1 = val1;
        this.op = op;
        this.val2 = val2;
    }
    

    public Fitness getFitness(){
        return fitness;
    }

    public void setFitness(Fitness fitness){
        this.fitness= fitness;
    }
    
	public String getDescription() {
		return toString();
	}
    
    public Solution clone(){
        return new ExprSolution(val1, op, val2);
    }
    
    public String toString(){
        return "("+val1+" "+op+" "+val2+")";
    }
    
    public String getDetails(){
    	String result = toString();

    	// Calcolo non ancora effettuato
        if(fitness==null){
            return result;
        }
        
        result += " => F:" + fitness.getLegalCheck()!=null ? "###" : String.valueOf(fitness);
        return result;
    }
    
    
    

	// TODOA: move in a recombinator class
	public Solution[] crossoverWith(Solution other){
        Solution[] sons = new Solution[2];
        ExprSolution ot = (ExprSolution)other;
        // I punti di ricombinazione possono essere all'operatore o al secondo operando
        int crossoverPoint = RANDOMIZER.nextInt(2);
        if(crossoverPoint==0){
            sons[0] = new ExprSolution(ot.val1,    op,        val2);
            sons[1] = new ExprSolution(val1,       ot.op,     ot.val2);
        } else {
            sons[0] = new ExprSolution(ot.val1,    ot.op,        val2);
            sons[1] = new ExprSolution(val1,       op,     ot.val2);
        }
        return sons;
    }
}
