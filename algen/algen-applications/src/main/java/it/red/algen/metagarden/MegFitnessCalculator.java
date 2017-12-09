package it.red.algen.metagarden;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.RawFitness;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.phenotype.UserPhenotype;
import it.red.algen.engine.core.MathUtils;
import it.red.algen.engine.fitness.AbstractFitnessCalculator;
import it.red.algen.metagarden.data.GardenWellness;

public class MegFitnessCalculator extends  AbstractFitnessCalculator<GenericSolution,StandardFitness> {
	private static final Logger logger = Logger.getLogger(MegFitnessCalculator.class);

	
	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * 
	 * TODOM-4: manage no-goal problems: concept of fitness boundaries
	 * @return
	 */
	@Override
	protected final RawFitness calculateRaw(GenericSolution solution, Env env){
        // Calculate global garden happiness
    	List<Double> locationsUnappiness = ((GardenWellness)((UserPhenotype)solution.phenotype).getValue()).locationsUnhappyness;
    	Double totalUnhappyness = locationsUnappiness.stream().mapToDouble(a -> a).sum();
    	return new NumberRawFitness(totalUnhappyness);
	}
		
	@Override
	protected final BigDecimal normalize(GenericSolution solution, Env env, RawFitness rawFitness){
		BigDecimal result = BigDecimal.ONE;
    	Double unhappyness = ((NumberRawFitness)rawFitness).value.doubleValue();
    	if(!MathUtils.equals(new BigDecimal(unhappyness), BigDecimal.ZERO)){
    		PerformanceTarget<String,Double> target = (PerformanceTarget<String,Double>)env.target;
    		BigDecimal bh = new BigDecimal(unhappyness).setScale(20, BigDecimal.ROUND_HALF_UP);
    		BigDecimal bt = new BigDecimal(target.getReferenceMeasure()).setScale(20, BigDecimal.ROUND_HALF_UP);
    		if("happy".equals(target.getGoal()) || "maximum".equals(target.getGoal())) {
    			result = BigDecimal.ONE.subtract(bh.divide(bt, BigDecimal.ROUND_HALF_UP));
    		}
    		else if("unhappy".equals(target.getGoal()) || "minimum".equals(target.getGoal())) {
    			result = bh.divide(bt, BigDecimal.ROUND_HALF_UP);
    		}
    	}
		return result;
	}

}
