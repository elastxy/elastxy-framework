/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package metaexpressions;

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
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.metaexpressions.MexBenchmark;
import it.red.algen.stats.Experiment;


/**
 *
 * @author grossi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MetaExpressionsMainTest {
   
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private EnvFactory envFactory;
	
	@Autowired
	private GenomaProvider genomaProvider;
	
	@Autowired
	private MexBenchmark mexBenchmark;
		
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Test
    public void simpleRun() {
		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());

		// Context
		AlgorithmContext context = mexBenchmark.build();
		contextSupplier.init(context);
		
		// Genoma
		context.mutator.setGenoma(genomaProvider.collect());

		// Experiment
		Experiment e = new Experiment(envFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        assertNotNull(e.getStats());
        assertNotNull(e.getStats().lastGeneration);
        
        contextSupplier.destroy();
    }
    
}