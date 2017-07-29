package it.red.algen.garden.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.AlgorithmContextBuilder;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.garden.conf.GardenConf;
import it.red.algen.garden.tracking.GardenCSVReporter;

@Component
public class GardenBenchmark implements BenchmarkContextBuilder {

	@Autowired
	private AlgorithmContextBuilder contextBuilder;
	
	public AlgorithmContext build() {
		AlgorithmContext context = contextBuilder.build(
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
