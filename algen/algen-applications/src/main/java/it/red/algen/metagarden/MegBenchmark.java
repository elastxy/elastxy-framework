package it.red.algen.metagarden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.AlgorithmContextBuilder;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.tracking.CSVReporter;

/**
 * TODOA: remove duplication with other benchmarks
 * @author red
 */
@Component
public class MegBenchmark implements BenchmarkContextBuilder {

	// Default values
	private static final String DEFAULT_TARGET = "happy"; // "happy"
	private static final boolean DEFAULT_LIMITED_TREES = true; // true
	
	// Odd number of solutions
    private static final long INITIAL_SELECTION_NUMBER = 100; // 100
    private static final boolean INITIAL_SELECTION_RANDOM = false; // false
    
    private static final int MAX_ITERATIONS = -1; // -1
    private static final int MAX_LIFETIME_MILLIS = 3000; // 3000
    private static final int MAX_IDENTICAL_FITNESSES = -1; // -1

    private static final boolean ELITARISM = true; // true
	private static final double RECOMBINANTION_PERC = 0.8; // 0.8
    private static final double MUTATION_PERC = 0.2; // 0.2

    private static final boolean VERBOSE = false; // false
    private static final boolean TRACE_HISTORY = false; // false
    
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
				new CSVReporter(MegApplication.STATS_DIR));

		GardenService.setupContext(context);

		context.applicationSpecifics.putTarget(MegApplication.TARGET_WELLNESS, DEFAULT_TARGET);
		context.applicationSpecifics.putParam(MegApplication.LIMITED_TREES, DEFAULT_LIMITED_TREES);
		return context;
	}


}
