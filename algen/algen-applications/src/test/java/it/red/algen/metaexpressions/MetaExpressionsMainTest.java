/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.metaexpressions;

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


/**
 *
 * @author grossi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MetaExpressionsMainTest {
   
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private MexEnvFactory envFactory;
	
	@Autowired private MexBenchmark benchmark;
		
	@Autowired private AutowireCapableBeanFactory beanFactory;
	
	
	@Test
    public void simpleRun() {
		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());

		// Context
		AlgorithmContext context = benchmark.build();
		contextSupplier.init(context);
		
		// Experiment
		Experiment e = new Experiment(envFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        assertNotNull(e.getStats());
        assertNotNull(e.getStats().lastGeneration);
        
        contextSupplier.destroy();
    }
    
}
