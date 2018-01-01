/*
 * Main.java
 *
 * Created on 4 agosto 2007, 14.01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.app.metagarden;

import static org.junit.Assert.*;

import java.io.File;

import org.elastxy.app.TestConfig;
import org.elastxy.core.applications.ApplicationService;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.context.RequestContext;
import org.elastxy.core.stats.ExperimentStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * TODO2-8: complete test coverage
 * @author grossi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MetaGardenMainTest {
	@Autowired private ApplicationService service;
	
	@Test
    public void simpleRun() {
		System.setProperty("datadir", new File("C:\\tmp\\elastxydata").getAbsolutePath());

		AlgorithmContext context = new AlgorithmContext();
		context.application.appName = "garden";
		context.requestContext = new RequestContext(false);
		ExperimentStats stats = service.executeBenchmark(context);

        assertNotNull(stats);
        assertNotNull(stats.bestMatch);
        assertNotNull(stats.lastGeneration);
        assertTrue(stats.bestMatch.getFitness().getValue().doubleValue() > 0.7);
    }
    
}
