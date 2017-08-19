package it.red.algen.metasudoku;

import java.math.BigDecimal;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.StandardFitness;
import it.red.algen.domain.genetics.ComplexPhenotype;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.IllegalSolutionException;
import it.red.algen.engine.Incubator;

public class MesFitnessCalculator implements FitnessCalculator<GenericSolution,StandardFitness> {

	private Incubator<SequenceGenotype,ComplexPhenotype> incubator;
	private Env env;
	
	@Override
	public void setup(Incubator incubator, Env environment){
		this.env = environment;
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
        	solution.phenotype = incubator.grow((SequenceGenotype)solution.genotype, env);
        	double sValue = (Double)((ComplexPhenotype)solution.phenotype).getValue().get(MesApplication.PHENOTYPE_COMPLETENESS);
            
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