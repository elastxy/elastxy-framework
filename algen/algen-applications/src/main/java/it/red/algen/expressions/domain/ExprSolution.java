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

import it.red.algen.domain.interfaces.Fitness;
import it.red.algen.domain.interfaces.Solution;

/**
 *
 * @author grossi
 */
public class ExprSolution implements Solution {
    
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
    
}
