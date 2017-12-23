package org.elastxy.core.conf;

import java.io.Serializable;
import java.math.BigDecimal;

public class StopConditions implements Serializable {
	public BigDecimal targetFitness =	DefaultConfiguration.TARGET_LEVEL;
	public BigDecimal targetThreshold = DefaultConfiguration.TARGET_THRESHOLD;
	
    public int maxGenerations = 		DefaultConfiguration.MAX_GENERATIONS;
    public int maxLifetimeMs = 			DefaultConfiguration.MAX_LIFETIME_MS;
    public int maxIdenticalFitnesses = 	DefaultConfiguration.MAX_IDENTICAL_FITNESSES;
    
    // DISTRIBUTED
    public int maxEras = 				DefaultConfiguration.DEFAULT_MAX_ERAS;
    public int maxErasIdenticalFitnesses = 	DefaultConfiguration.DEFAULT_MAX_ERAS_IDENTICAL_FITNESSES;
}
