package it.red.algen.expressions.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.AlgorithmContextBuilder;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.expressions.conf.ExprConf;
import it.red.algen.tracking.CSVReporter;

@Component
public class ExprBenchmark implements BenchmarkContextBuilder {

	@Autowired
	private AlgorithmContextBuilder contextBuilder;
	
	@Override
	public AlgorithmContext build() {
		AlgorithmContext context = contextBuilder.build(
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
