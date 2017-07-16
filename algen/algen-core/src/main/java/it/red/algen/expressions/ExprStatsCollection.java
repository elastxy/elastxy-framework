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

import it.red.algen.stats.StatsCollector;
import it.red.algen.tracking.CSVReporter;

/**
 *
 * @author grossi
 */
public class ExprStatsCollection {
    
    public static void main(String[] args) {
        StatsCollector collector = new StatsCollector(new ExprExperimentFactory(
        		10000, 
        		120, 
        		100,
        		false, 
        		new CSVReporter(ExprConf.MASSIVE_STATS_DIR)), 
        		100);
        collector.run();
        collector.print();
    }
    
}
