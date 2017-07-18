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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.red.algen.TestConfig;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.stats.Experiment;
import it.red.algen.tracking.CSVReporter;
import it.red.algen.tracking.LoggerManager;
import it.red.algen.tracking.SimpleLogger;

/**
 *
 * @author grossi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GardenMainTest {
    
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private GardenEnvFactory gardenEnvFactory;

	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Test
    public void simpleRun() {
		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());
		
        LoggerManager.instance().init(new SimpleLogger());
        
		AlgorithmContext context = AlgorithmContext.build(
				GardenConf.INITIAL_SELECTION_NUMBER,
				GardenConf.INITIAL_SELECTION_RANDOM,
				GardenConf.RECOMBINANTION_PERC, 
        		GardenConf.MUTATION_PERC, 
        		GardenConf.ELITARISM, 
        		GardenConf.MAX_ITERATIONS, 
        		GardenConf.MAX_LIFETIME_SEC, 
        		GardenConf.MAX_IDENTICAL_FITNESSES,
        		GardenConf.VERBOSE, 
        		new CSVReporter(GardenConf.STATS_DIR));
		
		contextSupplier.init(context);
		
		Experiment e = new Experiment(
        		null, // target already set in database data
        		gardenEnvFactory);
        
        beanFactory.autowireBean(e);
        
        e.run();
        
        assertNotNull(e.getStats());
        assertNotNull(e.getStats()._lastGeneration);

        contextSupplier.destroy();
	}
    
}
