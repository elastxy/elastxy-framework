package it.red.algen.conf;

import java.math.BigDecimal;

public class StopConditions {
	public BigDecimal targetFitness =	DefaultConfiguration.TARGET_LEVEL;
	public BigDecimal targetThreshold = DefaultConfiguration.TARGET_THRESHOLD;
	
    public int maxIterations = 			DefaultConfiguration.MAX_ITERATIONS;
    public int maxLifetimeMs = 			DefaultConfiguration.MAX_LIFETIME_MS;
    public int maxIdenticalFitnesses = 	DefaultConfiguration.MAX_IDENTICAL_FITNESSES;
}
