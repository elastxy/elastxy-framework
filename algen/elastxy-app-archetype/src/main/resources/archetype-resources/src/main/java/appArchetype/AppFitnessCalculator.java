package ${groupId}.appArchetype;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.NumberRawFitness;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.RawFitness;
import org.elastxy.core.domain.experiment.StandardFitness;
import org.elastxy.core.domain.genetics.phenotype.NumberPhenotype;
import org.elastxy.core.engine.fitness.AbstractFitnessCalculator;

public class AppFitnessCalculator extends  AbstractFitnessCalculator<GenericSolution,StandardFitness> {
	private static final Logger logger = Logger.getLogger(AppFitnessCalculator.class);

	
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
