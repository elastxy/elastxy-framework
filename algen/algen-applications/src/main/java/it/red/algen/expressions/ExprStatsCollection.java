/*
 * ExprStatsCollection.java
 *
 * Created on 5 agosto 2007, 15.30
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import it.red.algen.conf.AlgorithmContext;
import it.red.algen.stats.StatsExperimentExecutor;
import it.red.algen.tracking.CSVReporter;

/**
 *
 * @author grossi
 */
public class ExprStatsCollection {
    
    public static void main(String[] args) {
    	final int experimentsNumber = 3;
        
		AlgorithmContext context = AlgorithmContext.build(
				ExprConf.RECOMBINANTION_PERC, 
        		ExprConf.MUTATION_PERC, 
        		ExprConf.ELITARISM, 
        		10000, 
        		120000, 
        		100,
        		ExprConf.VERBOSE, 
        		new CSVReporter(ExprConf.MASSIVE_STATS_DIR));
		
        StatsExperimentExecutor collector = new StatsExperimentExecutor(
        		context,
        		new ExprExperimentFactory(), 
        		experimentsNumber, 
        		new ExprTarget(33));
        
        collector.run();
        collector.print();
    }
    
}
