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

/**
 *
 * @author grossi
 */
public class DefaultConfiguration {
    public static final long 	DEFAULT_INITIAL_SELECTION_NUMBER = 100L;
	public static final boolean DEFAULT_INITIAL_SELECTION_RANDOM = true;
    public static final boolean DEFAULT_ELITARISM = true;
	public static final double 	DEFAULT_RECOMBINANTION_PERC = 0.8;
    public static final double 	DEFAULT_MUTATION_PERC = 0.1;
    
    public static final int 	MAX_ITERATIONS = -1;
    public static final int 	MAX_LIFETIME_MS = 60000;
    public static final int 	MAX_IDENTICAL_FITNESSES = -1;

}
