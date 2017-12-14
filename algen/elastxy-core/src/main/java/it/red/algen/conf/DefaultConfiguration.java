/*
 * Conf.java
 *
 * Created on 5 agosto 2007, 15.37
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.conf;

import java.math.BigDecimal;

/**
 *
 * @author grossi
 */
public class DefaultConfiguration {
	
	// SELECTION
    public static final long 	DEFAULT_INITIAL_SELECTION_NUMBER = 100L;
	public static final boolean DEFAULT_INITIAL_SELECTION_RANDOM = true;
    public static final boolean DEFAULT_ELITARISM = true;
	
    // RECOMBINATION
    public static final double 	DEFAULT_RECOMBINANTION_PERC = 0.8;
    public static final boolean DEFAULT_CROSSOVER_POINT_RANDOM = false; // false: 1/2 of the sequence
    
    // MUTATION
    public static final double 	DEFAULT_MUTATION_PERC = 0.1;
    
    // STOP CONDITIONS
    public static final BigDecimal	TARGET_LEVEL = null; // Default: maximize
    public static final BigDecimal	TARGET_THRESHOLD = null;//BigDecimal.ONE; // Default: not active
    public static final int 		MAX_ITERATIONS = -1;
    public static final int 		MAX_LIFETIME_MS = 60000;
    public static final int 		MAX_IDENTICAL_FITNESSES = -1;
    
    // DISTRIBUTED
    public static final int 		DEFAULT_MAX_ERAS = 3;
    public static final int 		DEFAULT_MAX_ERAS_IDENTICAL_FITNESSES = -1;
    public static final int 		DEFAULT_ERA_BEST_MATCHES = 1;
    public static final int 		DEFAULT_PARTITIONS = 4;
    public static final int 		DEFAULT_RESHUFFLE_EVERY_ERAS = 2;
    public static final boolean		DEFAULT_MULTICOLONY_ELITARISM = true;
    

}
