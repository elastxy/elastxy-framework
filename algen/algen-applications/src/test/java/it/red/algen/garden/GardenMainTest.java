/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import it.red.algen.AlgParameters;
import it.red.algen.stats.Experiment;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;

/**
 *
 * @author grossi
 */
public class GardenMainTest {
    
	@Test
    public void simpleRun() {
		System.setProperty("basedir", new File("C:\\tmp\\algendata").getAbsolutePath());
		
        LoggerManager.instance().init(new SimpleLogger());
        AlgParameters.instance().init(
        		GardenConf.RECOMBINANTION_PERC, 
        		GardenConf.MUTATION_PERC, 
        		GardenConf.ELITARISM);
        Experiment e = new Experiment(
        		new GardenEnvFactory(), 
        		GardenConf.MAX_ITERATIONS, 
        		GardenConf.MAX_LIFETIME_SEC, 
        		GardenConf.MAX_IDENTICAL_FITNESSES,
        		GardenConf.VERBOSE,
        		new GardenCSVReporter(GardenConf.STATS_DIR));
        e.run();
        assertNotNull(e.getStats());
        assertNotNull(e.getStats()._lastGeneration);
    }
    
}
