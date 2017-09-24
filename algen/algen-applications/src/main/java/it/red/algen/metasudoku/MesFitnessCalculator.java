package it.red.algen.metasudoku;

import java.math.BigDecimal;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.phenotype.ComplexPhenotype;
import it.red.algen.engine.IllegalSolutionException;
import it.red.algen.engine.fitness.FitnessCalculator;
import it.red.algen.engine.fitness.Incubator;

public class MesFitnessCalculator implements FitnessCalculator<GenericSolution,StandardFitness> {

	private Incubator<Chromosome,ComplexPhenotype> incubator;
	
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
	public StandardFitness calculate(GenericSolution solution, Env env) {
        
		// Setup fitness
		StandardFitness result = new StandardFitness();
        solution.setFitness(result);
        
        // Setup variables
        BigDecimal solutionBD = null;
        BigDecimal normalized = BigDecimal.ZERO;
        PerformanceTarget<String,Integer> target = (PerformanceTarget<String,Integer>)env.target;
        String legalCheck = null;
        try { 
        	
        	// Grow the offspring to evaluate it
        	solution.phenotype = incubator.grow(env.workingDataset, (Chromosome)solution.genotype, env);
        	double sValue = (Double)((ComplexPhenotype)solution.phenotype).getValue().get(MesConstants.PHENOTYPE_COMPLETENESS);
            
        	// Normalize fitness to 1.0
        	solutionBD = new BigDecimal(sValue).setScale(20, BigDecimal.ROUND_HALF_UP);
        	BigDecimal targetBD = new BigDecimal(target.getReferenceMeasure()).setScale(20, BigDecimal.ROUND_HALF_UP);
            normalized = solutionBD.divide(targetBD, BigDecimal.ROUND_HALF_UP);
        } catch(IllegalSolutionException ex){ 
            legalCheck = String.format("Division by 0 not allowed: second operand not valid [%.10f].",target.getReferenceMeasure());
            normalized = BigDecimal.ZERO;
        }
        
        // Create fitness result
        result.setValue(normalized);
        result.setRawValue(new NumberRawFitness(solutionBD));
        result.setLegalCheck(legalCheck);
        return result;
    }

}
