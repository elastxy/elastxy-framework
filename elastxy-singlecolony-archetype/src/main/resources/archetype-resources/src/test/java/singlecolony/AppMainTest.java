/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package ${groupId}.singlecolony;

import static org.junit.Assert.*;

import java.io.File;

import ${groupId}.singlecolony.TestConfig;
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
