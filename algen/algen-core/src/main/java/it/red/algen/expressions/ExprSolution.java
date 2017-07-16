/*
 * ExprSolution.java
 *
 * Created on 4 agosto 2007, 14.40
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import it.red.algen.Fitness;
import it.red.algen.IllegalSolutionException;
import it.red.algen.Solution;
import it.red.algen.Target;
import java.util.Random;

/**
 *
 * @author grossi
 */
public class ExprSolution implements Solution {
    private static Random RANDOMIZER = new Random();
    private NumberGene _val1;
    private OperatorGene _op;
    private NumberGene _val2;
    
    private Fitness _fitness;
    private String _legalCheck;
    
    public ExprSolution(NumberGene val1, OperatorGene op, NumberGene val2) {
        _val1 = val1;
        _op = op;
        _val2 = val2;
    }
    
    public ExprSolution(int val1, char op,int val2) {
        ExprGenesFactory factory = new ExprGenesFactory();
        _val1 = factory.getNumber(val1);
        _op = factory.getOperator(op);
        _val2 = factory.getNumber(val2);
    }
    

	public String getRepresentation() {
		return toString();
	}
    
    public Fitness getFitness(){
        return _fitness;
    }
    
    public int compute() throws IllegalSolutionException {
        return _op.apply(_val1.getValue(), _val2.getValue());
    }
    
    public void calcFitness(Target target){
        ExprTarget t = (ExprTarget)target;
        int tValue = t.getComputeValue();
        int sValue = 0;
        double normalized = 0.0;
        try { 
            sValue = compute(); 
            int distance = Math.abs(tValue-sValue);
            normalized = 1 - distance / (double)t.getDistance();
        } catch(IllegalSolutionException ex){ 
            _legalCheck = "Divisione per 0 non ammessa: secondo operando non valido.";
            normalized = 0;
        }
        _fitness = new ExprFitness(normalized);
    }
    
    public String legalCheck(){
        return _legalCheck;
    }
    
    public Solution[] crossoverWith(Solution other){
        Solution[] sons = new Solution[2];
        ExprSolution ot = (ExprSolution)other;
        // I punti di ricombinazione possono essere all'operatore o al secondo operando
        int crossoverPoint = RANDOMIZER.nextInt(2);
        if(crossoverPoint==0){
            sons[0] = new ExprSolution(ot._val1,    _op,        _val2);
            sons[1] = new ExprSolution(_val1,       ot._op,     ot._val2);
        } else {
            sons[0] = new ExprSolution(ot._val1,    ot._op,        _val2);
            sons[1] = new ExprSolution(_val1,       _op,     ot._val2);
        }
        return sons;
    }
    
    /** Pi� probabile la mutazione dell'operatore
     */
    public void mute(){
        ExprGenesFactory factory = new ExprGenesFactory();
        switch(RANDOMIZER.nextInt(4)){
            case 0:
            	_val1 = factory.getRandomNumber();
                break;
            case 1:
            case 2:
            	_op = factory.getRandomOperator();
                break;
            case 3:
            	_val2 = factory.getRandomNumber();
                break;
        }
    }
    
    public Object clone(){
        return new ExprSolution(_val1, _op, _val2);
    }
    
    public String toString(){
        String solution = "("+_val1+" "+_op+" "+_val2+")";
        String details = getDetails();
        return solution+details;
    }
    
    private String getDetails(){
        // Calcolo non ancora effettuato
        if(_fitness==null && _legalCheck==null){
            return "";
        }
        double fitness = ((ExprFitness)_fitness).getValue();
        String res = _legalCheck!=null ? "###" : String.valueOf(fitness);
        return " => F:"+res;
    }
}
