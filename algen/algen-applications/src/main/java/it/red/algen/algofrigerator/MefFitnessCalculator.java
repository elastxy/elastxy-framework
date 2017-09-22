package it.red.algen.algofrigerator;

import java.math.BigDecimal;
import java.util.Map;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.genotype.Strand;
import it.red.algen.domain.genetics.phenotype.ComplexPhenotype;
import it.red.algen.engine.fitness.FitnessCalculator;
import it.red.algen.engine.fitness.Incubator;

public class MefFitnessCalculator implements FitnessCalculator<GenericSolution,StandardFitness> {
	private static BigDecimal WEIGHT_COMPLETENESS 		= new BigDecimal(0.8);
	private static BigDecimal WEIGHT_FOODS_FROM_FRIDGE 	= new BigDecimal(0.2);
	
	private Incubator<Strand,ComplexPhenotype> incubator;
	
	@Override
	public void setup(Incubator incubator){
		this.incubator = incubator;
	}
	
	
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
	public StandardFitness calculate(GenericSolution solution, Env env) {
        
		// Setup fitness
		StandardFitness result = new StandardFitness();
        solution.setFitness(result);
    	BigDecimal normalizedFitness = null;
        
    	// Grow the offspring to evaluate it
    	solution.phenotype = incubator.grow(env.genoma.getWorkingDataset(), (Strand)solution.genotype, env);

    	// Get goal
    	MefGoal goal = (MefGoal)env.target.getGoal();

    	// Check foods from fridge
    	BigDecimal foodsFromFridge = new BigDecimal((Double)((Map<String,Object>)solution.phenotype.getValue()).get(MefConstants.PHENOTYPE_PERCENTAGE_FOOD_FROM_FRIDGE)).setScale(3,  BigDecimal.ROUND_HALF_UP);
    	if(goal.fridgeMandatory && foodsFromFridge.setScale(3).equals(BigDecimal.ZERO.setScale(3))){
    		normalizedFitness = BigDecimal.ZERO;
            result.setValue(normalizedFitness);
            result.setRawValue(new NumberRawFitness(0.0));
            return result;
    	}

    	// Check completeness
    	BigDecimal completeness = null;
    	Double completeMeals = (Double)((Map<String,Object>)solution.phenotype.getValue()).get(MefConstants.PHENOTYPE_COMPLETENESS_POINTS);
    	BigDecimal completeMealsBD = new BigDecimal(completeMeals).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal desiredMealsBD = new BigDecimal(goal.desiredMeals).setScale(2, BigDecimal.ROUND_HALF_UP);;
    	completeness = completeMealsBD.compareTo(desiredMealsBD)==0 ? 
    			BigDecimal.ONE : 
    			completeMealsBD.divide(desiredMealsBD, BigDecimal.ROUND_HALF_UP);
        
    	// Weight different factors
    	normalizedFitness = completeness.multiply(WEIGHT_COMPLETENESS).add(foodsFromFridge.multiply(WEIGHT_FOODS_FROM_FRIDGE)); 
    	
        // Create fitness result
        result.setValue(normalizedFitness);
        result.setRawValue(new NumberRawFitness(completeMeals));
        return result;
    }

}
