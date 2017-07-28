/*
 * ExprConf.java
 *
 * Created on 5 agosto 2007, 15.39
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions.conf;

/**
 *
 * @author grossi
 */
public class ExprConf {

	// Constants
	public static final String TARGET_EXPRESSION_RESULT = "TARGET_EXPRESSION_RESULT";
	public static final String MAX_OPERAND_VALUE = "MAX_OPERAND_VALUE";
	
	
	// Default values
	public static final int DEFAULT_EXPRESSION_RESULT = 235000;
	
	public static final int DEFAULT_MAX_OPERAND_VALUE = 1000;
	
    public static final long INITIAL_SELECTION_NUMBER = 10;
    public static final boolean INITIAL_SELECTION_RANDOM = true;
    
    public static final int MAX_ITERATIONS = 1000;
    public static final int MAX_LIFETIME_MILLIS = 1000;
    public static final int MAX_IDENTICAL_FITNESSES = 100;

    public static final boolean ELITARISM = true;
	public static final double RECOMBINANTION_PERC = 0.8;
    public static final double MUTATION_PERC = 0.1;
    
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
