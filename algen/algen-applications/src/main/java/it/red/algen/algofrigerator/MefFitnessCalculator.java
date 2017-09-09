package it.red.algen.algofrigerator;

import java.math.BigDecimal;
import java.util.Map;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.ChromosomeGenotype;
import it.red.algen.domain.genetics.ComplexPhenotype;
import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.Incubator;

public class MefFitnessCalculator implements FitnessCalculator<GenericSolution,StandardFitness> {

	
	private Incubator<ChromosomeGenotype,ComplexPhenotype> incubator;
	
	@Override
	public void setup(Incubator incubator){
		this.incubator = incubator;
	}
	
	
	/**
	 * Produces the performing data of the individual,
	 * calculating recipes completeness.
	 * 
	 * TODOA: remove redundancy with other fitness calculator
	 * @return
	 */
	@Override
	public StandardFitness calculate(GenericSolution solution, Env env) {
        
		// Setup fitness
		StandardFitness result = new StandardFitness();
        solution.setFitness(result);
        
    	// Grow the offspring to evaluate it
    	solution.phenotype = incubator.grow(env.genoma.getWorkingDataset(), (ChromosomeGenotype)solution.genotype, env);
        
    	// Get points
    	Double completeMeals = (Double)((Map<String,Object>)solution.phenotype.getValue()).get(MefApplication.PHENOTYPE_COMPLETENESS_POINTS);
    	BigDecimal completeMealsBD = new BigDecimal(completeMeals).setScale(2, BigDecimal.ROUND_HALF_UP);
    	
    	// Normalize fitness to 1.0
    	BigDecimal normalized = null;
    	MefGoal goal = (MefGoal)env.target.getGoal();
    	BigDecimal desiredMealsBD = new BigDecimal(goal.desiredMeals).setScale(2, BigDecimal.ROUND_HALF_UP);;
    	
    	if(completeMealsBD.compareTo(desiredMealsBD)==0){
    		normalized = BigDecimal.ONE;
    	}
    	else {
    		normalized = completeMealsBD.divide(desiredMealsBD, BigDecimal.ROUND_HALF_UP);
    	}
        
        // Create fitness result
        result.setValue(normalized);
        result.setRawValue(new NumberRawFitness(completeMeals));
        return result;
    }

}
