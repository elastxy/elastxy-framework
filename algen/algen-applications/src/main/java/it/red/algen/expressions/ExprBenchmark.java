package it.red.algen.expressions;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.tracking.CSVReporter;

public class ExprBenchmark implements BenchmarkContextBuilder {

	@Override
	public AlgorithmContext build() {
		AlgorithmContext context = AlgorithmContext.build(
				ExprConf.INITIAL_SELECTION_NUMBER,
				ExprConf.INITIAL_SELECTION_RANDOM,
				ExprConf.RECOMBINANTION_PERC, 
				ExprConf.MUTATION_PERC, 
				ExprConf.ELITARISM, 
				ExprConf.MAX_ITERATIONS, 
				ExprConf.MAX_LIFETIME_MILLIS, 
				ExprConf.MAX_IDENTICAL_FITNESSES,
				ExprConf.VERBOSE, 
				new CSVReporter(ExprConf.STATS_DIR));

//		context.applicationSpecifics.target = Optional.of(new ExprTarget(ExprConf.DEFAULT_EXPRESSION_RESULT));
		context.applicationSpecifics.putTarget(ExprConf.TARGET_EXPRESSION_RESULT, ExprConf.DEFAULT_EXPRESSION_RESULT);
		context.applicationSpecifics.putParam(ExprConf.MAX_OPERAND_VALUE, ExprConf.DEFAULT_MAX_OPERAND_VALUE);
		return context;
	}


}
