/*
 * ExprConf.java
 *
 * Created on 5 agosto 2007, 15.39
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

/**
 *
 * @author grossi
 */
public class GardenConf {
	
	public static final int FITNESS_WEIGHT_SUN = 5;
	public static final int FITNESS_WEIGHT_WIND = 3;
	public static final int FITNESS_WEIGHT_WET = 1;
	
	public static final int INITIAL_POPULATION = 100; // numero pari
    public static final int MAX_ITERATIONS = 10000;
    public static final int MAX_LIFETIME_SEC = 120;
    public static final Integer MAX_IDENTICAL_FITNESSES = 2000;
    
    public static final boolean ELITARISM = true;
	public static final double RECOMBINANTION_PERC = 0.7;
    public static final double MUTATION_PERC = 0.2;
    
    public static final boolean VERBOSE = false;
    
    public static String BASE_DIR = System.getProperty("datadir");
	static {
        BASE_DIR = BASE_DIR==null ? "C:\\tmp\\algendata" : BASE_DIR;
        System.out.println("Set BASEDIR "+BASE_DIR);
	}
    
    public static final String DATABASE_DIR = 		BASE_DIR + "/garden/db";
    public static final String STATS_DIR = 			BASE_DIR + "/garden/stats";
    public static final String MASSIVE_STATS_DIR = 	BASE_DIR + "/garden/massive-stats";
    
}
