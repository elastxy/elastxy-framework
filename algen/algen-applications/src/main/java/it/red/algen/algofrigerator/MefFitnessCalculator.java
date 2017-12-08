package it.red.algen.algofrigerator;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.RawFitness;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.engine.fitness.AbstractFitnessCalculator;

public class MefFitnessCalculator extends  AbstractFitnessCalculator<GenericSolution,StandardFitness> {
	private static final Logger logger = Logger.getLogger(MefFitnessCalculator.class);
	
	private static BigDecimal WEIGHT_COMPLETENESS 		= new BigDecimal(0.8);
	private static BigDecimal WEIGHT_FOODS_FROM_FRIDGE 	= new BigDecimal(0.2);
	
	
	/**
	 * Produces the performing data of the individual,
	 * calculating target factor:
	 * - recipes completeness => weight 80%
	 * - used foods from fridge instead of pantry => weight 20% (dfault) OR mandatory, depends on user choice
	 * 
	 * TODOA: remove redundancy with other fitness calculator
	 * @return
	 */
	@Override
	protected final RawFitness calculateRaw(GenericSolution solution, Env env){
        RawFitness rawFitness = null;
		MefGoal goal = (MefGoal)env.target.getGoal();
    	// Check foods from fridge
    	BigDecimal foodsFromFridge = new BigDecimal((Double)((Map<String,Object>)solution.phenotype.getValue()).get(MefConstants.PHENOTYPE_PERCENTAGE_FOOD_FROM_FRIDGE)).setScale(3,  BigDecimal.ROUND_HALF_UP);
    	boolean noFridgeFoods = goal.fridgeMandatory && foodsFromFridge.setScale(3).equals(BigDecimal.ZERO.setScale(3));
    	if(noFridgeFoods){
    		rawFitness = new NumberRawFitness(0.0);
    	}
    	else {
    		// Check completeness
    		Double completeMeals = (Double)((Map<String,Object>)solution.phenotype.getValue()).get(MefConstants.PHENOTYPE_COMPLETENESS_POINTS);
    		rawFitness = new NumberRawFitness(completeMeals);
    	}
    	return rawFitness;
	}
	
	@Override
	protected final BigDecimal normalize(GenericSolution solution, Env env, RawFitness rawFitness){
        BigDecimal normalizedFitness = null;
        
		// TODOA: utilities compare ZERO!
        MefGoal goal = (MefGoal)env.target.getGoal();
        BigDecimal completeMeals = new BigDecimal(((NumberRawFitness)rawFitness).value.doubleValue());
        boolean noFridgeFoods = goal.fridgeMandatory && completeMeals.setScale(3, BigDecimal.ROUND_HALF_UP).equals(BigDecimal.ZERO.setScale(3, BigDecimal.ROUND_HALF_UP));
    	if(noFridgeFoods){
    		normalizedFitness = BigDecimal.ZERO;
    	}
    	else {
    		BigDecimal completeMealsBD = new BigDecimal(((NumberRawFitness)rawFitness).value.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
    		BigDecimal desiredMealsBD = new BigDecimal(goal.desiredMeals).setScale(2, BigDecimal.ROUND_HALF_UP);
    	
    		// TODOA-2: completeness over 0!!!! check Accumulator and neutral recipes usage...
    		BigDecimal completeness = completeMealsBD.compareTo(desiredMealsBD)==0 ? 
    			BigDecimal.ONE : 
    			completeMealsBD.divide(desiredMealsBD, BigDecimal.ROUND_HALF_UP);
    		// Weight different factors
    		BigDecimal foodsFromFridge = new BigDecimal((Double)((Map<String,Object>)solution.phenotype.getValue()).get(MefConstants.PHENOTYPE_PERCENTAGE_FOOD_FROM_FRIDGE)).setScale(3,  BigDecimal.ROUND_HALF_UP);
        	normalizedFitness = completeness.multiply(WEIGHT_COMPLETENESS).add(foodsFromFridge.multiply(WEIGHT_FOODS_FROM_FRIDGE)); 
    	}
    	return normalizedFitness;
	}
	
}
