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
import it.red.algen.domain.Target;
import it.red.algen.engine.IllegalSolutionException;

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
    private String legalCheck;
    
    public ExprSolution(NumberGene val1, OperatorGene op, NumberGene val2) {
    	this.val1 = val1;
        this.op = op;
        this.val2 = val2;
    }
    

	public String getRepresentation() {
		return toString();
	}
    
    public Fitness getFitness(){
        return fitness;
    }
    
    public int compute() throws IllegalSolutionException {
        return op.apply(val1.getValue(), val2.getValue());
    }
    
    public void calcFitness(Target target){
        ExprTarget t = (ExprTarget)target;
        int tValue = t.getComputeValue();
        int sValue = 0;
        double normalized = 0.0;
        try { 
            sValue = compute(); 
            int distance = Math.abs(tValue-sValue);
            normalized = 1 - distance / (double)((ExprRawFitness)t.getRawFitness()).distance;
        } catch(IllegalSolutionException ex){ 
            legalCheck = "Divisione per 0 non ammessa: secondo operando non valido.";
            normalized = 0;
        }
        fitness = new ExprFitness(normalized);
    }
    
    public String legalCheck(){
        return legalCheck;
    }
    
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
    
    public Object clone(){
        return new ExprSolution(val1, op, val2);
    }
    
    public String toString(){
        String solution = "("+val1+" "+op+" "+val2+")";
        String details = getDetails();
        return solution+details;
    }
    
    private String getDetails(){
        // Calcolo non ancora effettuato
        if(fitness==null && legalCheck==null){
            return "";
        }
        double fitness = ((ExprFitness)this.fitness).getValue();
        String res = legalCheck!=null ? "###" : String.valueOf(fitness);
        return " => F:"+res;
    }
}
