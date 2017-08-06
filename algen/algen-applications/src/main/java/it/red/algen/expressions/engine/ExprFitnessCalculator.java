package it.red.algen.expressions.engine;

import it.red.algen.engine.IllegalSolutionException;
import it.red.algen.engine.interfaces.FitnessCalculator;
import it.red.algen.engine.standard.StandardFitness;
import it.red.algen.expressions.domain.ExprRawFitness;
import it.red.algen.expressions.domain.ExprSolution;
import it.red.algen.expressions.domain.ExprTarget;

public class ExprFitnessCalculator implements FitnessCalculator<ExprSolution,ExprTarget,StandardFitness,Long> {

	@Override
	public StandardFitness calculate(ExprSolution solution, ExprTarget target) {
        long tValue = target.getComputeValue();
        long sValue = 0;
        double normalized = 0.0;
        String legalCheck = null;
        try { 
            sValue = perform(solution); 
            long distance = Math.abs(tValue-sValue);
            normalized = 1 - distance / (double)((ExprRawFitness)target.getRawFitness()).distance;
        } catch(IllegalSolutionException ex){ 
            legalCheck = "Divisione per 0 non ammessa: secondo operando non valido.";
            normalized = 0;
        }
        StandardFitness fitness = new StandardFitness();
        fitness.setValue(normalized);
        fitness.setLegalCheck(legalCheck);
        solution.setFitness(fitness);
        return fitness;
    }


	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * @return
	 */
	@Override
    public Long perform(ExprSolution solution) {
        return solution.op.apply(solution.val1.getValue(), solution.val2.getValue());
    }
    
}
