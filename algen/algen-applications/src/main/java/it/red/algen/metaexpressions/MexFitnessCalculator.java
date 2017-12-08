package it.red.algen.metaexpressions;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.RawFitness;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.phenotype.NumberPhenotype;
import it.red.algen.engine.core.IllegalSolutionException;
import it.red.algen.engine.fitness.AbstractFitnessCalculator;
import it.red.algen.engine.fitness.FitnessCalculator;
import it.red.algen.engine.fitness.Incubator;

public class MexFitnessCalculator extends  AbstractFitnessCalculator<GenericSolution,StandardFitness> {
	private static final Logger logger = Logger.getLogger(MexFitnessCalculator.class);

	
	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * 
	 * @return
	 */
	@Override
	protected final RawFitness calculateRaw(GenericSolution solution, Env env){
        PerformanceTarget<Long,BigDecimal> target = (PerformanceTarget<Long,BigDecimal>)env.target;
    	long sValue = ((NumberPhenotype)solution.phenotype).getValue().longValue();
        
    	// Calculate distance from goal
    	long tValue = (long)target.getGoal();
    	long distance = Math.abs(tValue-sValue);
    	return new NumberRawFitness(distance);
	}
		
	@Override
	protected final BigDecimal normalize(GenericSolution solution, Env env, RawFitness rawFitness){
		Double distance = ((NumberRawFitness)rawFitness).value.doubleValue();
        PerformanceTarget<Long,BigDecimal> target = (PerformanceTarget<Long,BigDecimal>)env.target;
        return BigDecimal.ONE.subtract(new BigDecimal(distance).setScale(20).divide(target.getReferenceMeasure().setScale(20), BigDecimal.ROUND_HALF_UP));
	}
	
	
}
