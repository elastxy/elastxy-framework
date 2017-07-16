/*
 * ExprConf.java
 *
 * Created on 5 agosto 2007, 15.39
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

/**
 *
 * @author grossi
 */
public class ExprConf {
	public static final int TARGET = -10;

    public static final int INITIAL_POPULATION = 1000;
    public static final int MAX_ITERATIONS = 10;
    public static final int MAX_LIFETIME_SEC = 60;
    public static final int MAX_IDENTICAL_FITNESSES = 3;

    public static final boolean ELITARISM = true;
	public static final double RECOMBINANTION_PERC = 0.7;
    public static final double MUTATION_PERC = 0.2;
    
    public static final boolean VERBOSE = false;
    
    public static String BASE_DIR = System.getProperty("basedir");
	static {
        BASE_DIR = BASE_DIR==null ? "C:\\tmp\\algendata" : BASE_DIR;
	}

    public static final String DATABASE_DIR = 		BASE_DIR + "/expressions/db";
    public static final String STATS_DIR = 			BASE_DIR + "/expressions/stats";
    public static final String MASSIVE_STATS_DIR = 	BASE_DIR + "/expressions/massive-stats";
}
