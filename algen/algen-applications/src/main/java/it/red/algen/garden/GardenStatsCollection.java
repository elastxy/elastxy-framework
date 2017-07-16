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

import it.red.algen.stats.StatsCollector;

/**
 *
 * @author grossi
 */
public class GardenStatsCollection {
    
    public static void main(String[] args) {
        StatsCollector collector = new StatsCollector(new GardenExperimentFactory(
        		1000, 
        		120,
        		100,
        		false,
        		new GardenCSVReporter(GardenConf.MASSIVE_STATS_DIR)
        		), 
        		3);
        collector.run();
        collector.print();
    }
    
}
