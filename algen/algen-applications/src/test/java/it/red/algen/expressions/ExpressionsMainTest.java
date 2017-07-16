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

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.red.algen.AlgParameters;
import it.red.algen.TestConfig;
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
public class ExpressionsMainTest {
   
	@Autowired
	private ExprEnvFactory exprEnvFactory;
	
	@Test
    public void simpleRun() {
		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());

		LoggerManager.instance().init(new SimpleLogger());
		AlgParameters algParameters = new AlgParameters();
		algParameters.init(
        		ExprConf.RECOMBINANTION_PERC, 
        		ExprConf.MUTATION_PERC, 
        		ExprConf.ELITARISM);
        Experiment e = new Experiment(
        		algParameters,
        		new ExprTarget(ExprConf.TARGET),
        		exprEnvFactory,
        		ExprConf.MAX_ITERATIONS, 
        		ExprConf.MAX_LIFETIME_SEC, 
        		ExprConf.MAX_IDENTICAL_FITNESSES,
        		ExprConf.VERBOSE, 
        		new CSVReporter(ExprConf.STATS_DIR));
        e.run();
        assertNotNull(e.getStats());
        assertNotNull(e.getStats()._lastGeneration);
    }
    
}
