/*
 * ExprStatsCollection.java
 *
 * Created on 5 agosto 2007, 15.30
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

import it.red.algen.conf.AlgorithmContext;
import it.red.algen.expressions.ExprConf;
import it.red.algen.expressions.ExprExperimentFactory;
import it.red.algen.stats.StatsExperimentExecutor;

/**
 *
 * @author grossi
 */
public class GardenStatsCollection {
    
    public static void main(String[] args) {
    	final int experimentsNumber = 3;
        
		AlgorithmContext context = AlgorithmContext.build(
        		GardenConf.INITIAL_SELECTION_NUMBER,
        		GardenConf.INITIAL_SELECTION_RANDOM,
				GardenConf.RECOMBINANTION_PERC, 
        		GardenConf.MUTATION_PERC, 
        		GardenConf.ELITARISM,
        		10000, 
        		120000, 
        		100,
        		ExprConf.VERBOSE, 
        		new GardenCSVReporter(GardenConf.MASSIVE_STATS_DIR));
		
        StatsExperimentExecutor collector = new StatsExperimentExecutor(
        		context,
        		new ExprExperimentFactory(), 
        		experimentsNumber, 
        		null);
        collector.run();
        collector.print();
    }
    
}
