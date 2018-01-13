package ${groupId}.appArchetype;

import static org.junit.Assert.*;

import java.io.File;

import ${groupId}.appArchetype.TestConfig;
import org.elastxy.core.applications.ApplicationService;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.context.RequestContext;
import org.elastxy.core.stats.ExperimentStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppMainTest {
	@Autowired private ApplicationService service;
	
	@Test
    public void simpleRun() {
		AlgorithmContext context = new AlgorithmContext();
		context.application.appName = "${artifactId}";
		context.requestContext = new RequestContext(false);
		
		ExperimentStats stats = service.executeBenchmark(context);
		
        assertNotNull(stats);
        assertNotNull(stats.bestMatch);
        assertNotNull(stats.lastGeneration);
		
		// Optional: asserts a minimum fitness value
        assertTrue(stats.bestMatch.getFitness().getValue().doubleValue() > 0.9);
    }
    
}
