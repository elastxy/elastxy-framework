package it.red.algen.garden;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.BenchmarkContextBuilder;

public class GardenBenchmark implements BenchmarkContextBuilder {

	public AlgorithmContext build() {
		AlgorithmContext context = AlgorithmContext.build(
				GardenConf.INITIAL_SELECTION_NUMBER,
				GardenConf.INITIAL_SELECTION_RANDOM,
				GardenConf.RECOMBINANTION_PERC, 
				GardenConf.MUTATION_PERC, 
				GardenConf.ELITARISM, 
				GardenConf.MAX_ITERATIONS, 
				GardenConf.MAX_LIFETIME_SEC, 
				GardenConf.MAX_IDENTICAL_FITNESSES,
				GardenConf.VERBOSE, 
				new GardenCSVReporter(GardenConf.STATS_DIR));
		return context;
	}
	
}
