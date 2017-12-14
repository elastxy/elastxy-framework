package org.elastxy.app.metasudoku;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.NumberRawFitness;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.RawFitness;
import org.elastxy.core.domain.experiment.StandardFitness;
import org.elastxy.core.domain.genetics.phenotype.ComplexPhenotype;
import org.elastxy.core.engine.fitness.AbstractFitnessCalculator;

public class MesFitnessCalculator extends  AbstractFitnessCalculator<GenericSolution,StandardFitness> {
	private static final Logger logger = Logger.getLogger(MesFitnessCalculator.class);

	/**
	 * Produces the performing data of the individual.
	 * E.g. for the expression individual, is the computed value of its expression
	 * 
	 * @return
	 */
	@Override
	protected final RawFitness calculateRaw(GenericSolution solution, Env env){
    	double sValue = (Double)((ComplexPhenotype)solution.phenotype).getValue().get(MesConstants.PHENOTYPE_COMPLETENESS);
    	BigDecimal solutionBD = new BigDecimal(sValue).setScale(20, BigDecimal.ROUND_HALF_UP);
    	return new NumberRawFitness(solutionBD);
	}
		
	@Override
	protected final BigDecimal normalize(GenericSolution solution, Env env, RawFitness rawFitness){
    	PerformanceTarget<String,Integer> target = (PerformanceTarget<String,Integer>)env.target;
    	BigDecimal targetBD = new BigDecimal(target.getReferenceMeasure()).setScale(20, BigDecimal.ROUND_HALF_UP);
    	BigDecimal solutionBD = new BigDecimal(((NumberRawFitness)rawFitness).value.doubleValue()).setScale(20, BigDecimal.ROUND_HALF_UP);
    	return solutionBD.divide(targetBD, BigDecimal.ROUND_HALF_UP);
	}


}
