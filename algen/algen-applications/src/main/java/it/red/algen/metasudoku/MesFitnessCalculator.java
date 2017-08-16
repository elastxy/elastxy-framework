package it.red.algen.metasudoku;

import java.math.BigDecimal;

import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.NumberPhenotype;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.IllegalSolutionException;
import it.red.algen.engine.Incubator;

public class MesFitnessCalculator implements FitnessCalculator<GenericSolution,PerformanceTarget<String,Integer>,StandardFitness> {

	
	private Incubator<SequenceGenotype,NumberPhenotype> incubator;
	
	@Override
	public void setup(Incubator incubator){
		this.incubator = incubator;
	}
	
	
	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * @return
	 */
	@Override
	public StandardFitness calculate(GenericSolution solution, PerformanceTarget<String,Integer> target) {
        
		// Setup fitness
		StandardFitness result = new StandardFitness();
        solution.setFitness(result);
        
        // Setup variables
        long distance = Long.MAX_VALUE;
        BigDecimal normalized = BigDecimal.ZERO;
        String legalCheck = null;
        try { 
        	
        	// Grow the offspring to evaluate it
        	solution.phenotype = incubator.grow((SequenceGenotype)solution.genotype);
        	int sValue = ((NumberPhenotype)solution.phenotype).getValue().intValue();
            
        	// Calculate distance from goal
        	int tValue = target.getReferenceMeasure();
        	distance = Math.abs(tValue-sValue);
        	
        	// Normalize fitness to 1.0
            normalized = BigDecimal.ONE.subtract(new BigDecimal(distance).setScale(20).divide(new BigDecimal(target.getReferenceMeasure()), BigDecimal.ROUND_HALF_UP));
        } catch(IllegalSolutionException ex){ 
            legalCheck = String.format("Division by 0 not allowed: second operand not valid [%.10f].",target.getReferenceMeasure());
            normalized = BigDecimal.ZERO;
        }
        
        // Create fitness result
        result.setValue(normalized);
        result.setRawValue(new NumberRawFitness(distance));
        result.setLegalCheck(legalCheck);
        return result;
    }

}
