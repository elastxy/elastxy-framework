package it.red.algen.metasudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.AlgorithmContextBuilder;
import it.red.algen.context.BenchmarkContextBuilder;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.StandardMutator;
import it.red.algen.engine.StandardSelector;
import it.red.algen.metaexpressions.MexApplication;
import it.red.algen.tracking.CSVReporter;


/**
 * TODOA: remove duplications with other benchmarks!
 * 
 * @author red
 *
 */
@Component
public class MesBenchmark implements BenchmarkContextBuilder {

	// Default values
    private static final long INITIAL_SELECTION_NUMBER = 100;
    private static final boolean INITIAL_SELECTION_RANDOM = true;
    
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

		context.incubator = new MesIncubator();

		context.fitnessCalculator = new MesFitnessCalculator();
		context.fitnessCalculator.setup(context.incubator);
		
		context.selector = new StandardSelector();
		context.selector.setup(context.parameters);
		
		context.mutator = new StandardMutator();
		
		context.recombinator = new SequenceRecombinator();
		
		context.monitoringConfiguration.solutionRenderer = new MesSolutionRenderer();
		
		return context;
	}


}
