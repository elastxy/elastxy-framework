/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import it.red.algen.AlgParameters;
import it.red.algen.stats.Experiment;
import it.red.algen.tracking.CSVReporter;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;

/**
 *
 * @author grossi
 */
public class ExpressionsMain {
    
    public static void main(String[] args) {
        LoggerManager.instance().init(new SimpleLogger());
        AlgParameters.instance().init(
        		ExprConf.RECOMBINANTION_PERC, 
        		ExprConf.MUTATION_PERC, 
        		ExprConf.ELITARISM);
        Experiment e = new Experiment(
        		new ExprEnvFactory(ExprConf.TARGET), 
        		ExprConf.MAX_ITERATIONS, 
        		ExprConf.MAX_LIFETIME_SEC, 
        		ExprConf.MAX_IDENTICAL_FITNESSES,
        		ExprConf.VERBOSE, 
        		new CSVReporter(ExprConf.STATS_DIR));
        e.run();
    }
    
}
