package it.red.algen.metaexpressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.AlgorithmContextBuilder;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.tracking.CSVReporter;


/**
 * TODOA: remove duplication with other benchmarks
 * @author red
 *
 */
@Component
public class MexBenchmark implements BenchmarkContextBuilder {

	// Default values
	private static final int DEFAULT_EXPRESSION_RESULT = 235000;
	
	private static final int DEFAULT_MAX_OPERAND_VALUE = 1000;
	
    private static final long INITIAL_SELECTION_NUMBER = 100;
    private static final boolean INITIAL_SELECTION_RANDOM = false;
    
    private static final int MAX_ITERATIONS = -1;
    private static final int MAX_LIFETIME_MILLIS = 3000;
    private static final int MAX_IDENTICAL_FITNESSES = -1;

    private static final boolean ELITARISM = true;
	private static final double RECOMBINANTION_PERC = 0.8;
    private static final double MUTATION_PERC = 0.2;

    private static final boolean VERBOSE = false;
    private static final boolean TRACE_HISTORY = false;
    
    @Autowired private AlgorithmContextBuilder contextBuilder;
	
	
	@Override
	public AlgorithmContext build() {
		AlgorithmContext context = contextBuilder.build(
				INITIAL_SELECTION_NUMBER,
				INITIAL_SELECTION_RANDOM,
				RECOMBINANTION_PERC, 
				MUTATION_PERC, 
				ELITARISM, 
				MAX_ITERATIONS, 
				MAX_LIFETIME_MILLIS, 
				MAX_IDENTICAL_FITNESSES,
				VERBOSE, 
				TRACE_HISTORY,
				new CSVReporter(MexApplication.STATS_DIR));
		
		ExpressionsService.setupContext(context);
		
		context.applicationSpecifics.putTarget(MexApplication.TARGET_EXPRESSION_RESULT, DEFAULT_EXPRESSION_RESULT);
		context.applicationSpecifics.putParam(MexApplication.MAX_OPERAND_VALUE, DEFAULT_MAX_OPERAND_VALUE);
		return context;
	}


}
