package it.red.algen.garden.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.AlgorithmContextBuilder;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.garden.engine.GardenApplication;
import it.red.algen.garden.tracking.GardenCSVReporter;

@Component
public class GardenBenchmark implements BenchmarkContextBuilder {
	private static final long INITIAL_SELECTION_NUMBER = 100; // numero pari
	private static final boolean INITIAL_SELECTION_RANDOM = true;
	
    private static final int MAX_ITERATIONS = 10000;
    private static final int MAX_LIFETIME_SEC = 120;
    private static final Integer MAX_IDENTICAL_FITNESSES = 2000;
    
    private static final boolean ELITARISM = true;
	private static final double RECOMBINANTION_PERC = 0.7;
    private static final double MUTATION_PERC = 0.2;
    
    private static final boolean VERBOSE = false;

	@Autowired
	private AlgorithmContextBuilder contextBuilder;
	
	public AlgorithmContext build() {
		AlgorithmContext context = contextBuilder.build(
				INITIAL_SELECTION_NUMBER,
				INITIAL_SELECTION_RANDOM,
				RECOMBINANTION_PERC, 
				MUTATION_PERC, 
				ELITARISM, 
				MAX_ITERATIONS, 
				MAX_LIFETIME_SEC, 
				MAX_IDENTICAL_FITNESSES,
				VERBOSE, 
				new GardenCSVReporter(GardenApplication.STATS_DIR));
		return context;
	}
	
}
