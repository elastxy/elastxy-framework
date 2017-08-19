package it.red.algen.metagarden;

import java.math.BigDecimal;
import java.util.List;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.domain.genetics.UserPhenotype;
import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.Incubator;
import it.red.algen.metagarden.data.GardenWellness;

public class MegFitnessCalculator implements FitnessCalculator<GenericSolution,StandardFitness> {

	
	private Incubator<SequenceGenotype,UserPhenotype> incubator;
	
	@Override
	public void setup(Incubator incubator){
		this.incubator = incubator;
	}
	
	
	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * 
	 * TODOA: remove redundancy
	 * TODOM: manage no-goal problems
	 * @return
	 */
	@Override
	public StandardFitness calculate(GenericSolution solution, Env env) {
        
		// Setup fitness
		StandardFitness result = new StandardFitness();
        solution.setFitness(result);
        
    	// Grow the offspring to evaluate it
    	solution.phenotype = incubator.grow((SequenceGenotype)solution.genotype, null);
//    	List<Double> locationsUnappiness = ((UserPhenotype<GardenWellness>)solution.phenotype).getValue().locationsUnhappyness;
    	List<Double> locationsUnappiness = ((GardenWellness)((UserPhenotype)solution.phenotype).getValue()).locationsUnhappyness;
        
    	// Calculate global garden happiness
//        	OptionalDouble averageWellness = locationsHappiness.stream().mapToDouble(a -> a).average();
    	Double totalUnhappyness = locationsUnappiness.stream().mapToDouble(a -> a).sum();
    	
    	// Normalize fitness to 1.0
    	BigDecimal normalized = null;
    	
    	if(totalUnhappyness.compareTo(0.0)==0){
    		normalized = BigDecimal.ONE;
    	}
    	else {
    		PerformanceTarget<String,Double> target = (PerformanceTarget<String,Double>)env.target;
    		BigDecimal bh = new BigDecimal(totalUnhappyness).setScale(20, BigDecimal.ROUND_HALF_UP);
    		BigDecimal bt = new BigDecimal(target.getReferenceMeasure()).setScale(20, BigDecimal.ROUND_HALF_UP);
    		if("happy".equals(target.getGoal()) || "maximum".equals(target.getGoal())) {
    			normalized = BigDecimal.ONE.subtract(bh.divide(bt, BigDecimal.ROUND_HALF_UP));
    		}
    		else if("unhappy".equals(target.getGoal()) || "minimum".equals(target.getGoal())) {
    			normalized = bh.divide(bt, BigDecimal.ROUND_HALF_UP);
    		}
    	}
        
        // Create fitness result
        result.setValue(normalized);
        result.setRawValue(new NumberRawFitness(totalUnhappyness));
        return result;
    }

}
