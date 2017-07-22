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

	// Constants
	public static final String MAX_OPERAND_VALUE = "MAX_OPERAND_VALUE"; // 32000
	
	
	// Default values
	public static final int DEFAULT_MAX_OPERAND_VALUE = 32000;
	
    public static final long INITIAL_SELECTION_NUMBER = 10;
    public static final boolean INITIAL_SELECTION_RANDOM = false;
    
    public static final int MAX_ITERATIONS = 10;
    public static final int MAX_LIFETIME_SEC = 60;
    public static final int MAX_IDENTICAL_FITNESSES = 3;

    public static final boolean ELITARISM = true;
	public static final double RECOMBINANTION_PERC = 0.7;
    public static final double MUTATION_PERC = 0.2;
    
    public static final boolean VERBOSE = false;
    
    
    // Data directory
    public static String BASE_DIR = System.getProperty("datadir");
	static {
        BASE_DIR = BASE_DIR==null ? "C:\\tmp\\algendata" : BASE_DIR;
	}

    public static final String DATABASE_DIR = 		BASE_DIR + "/expressions/db";
    public static final String STATS_DIR = 			BASE_DIR + "/expressions/stats";
    public static final String MASSIVE_STATS_DIR = 	BASE_DIR + "/expressions/massive-stats";
}
