package it.red.algen.distributed.sandbox;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.applications.ApplicationService;
import it.red.algen.stats.ExperimentStats;

@Component
public class ConcurrentTask {
	private static Logger logger = LoggerFactory.getLogger(ConcurrentTask.class);

	
//	@SuppressWarnings("unused")
//	@Autowired
//	private JavaSparkContext sparkContext;
	
	@Autowired private ApplicationService applicationService;
    
	
    public String run(String application) throws ExecutionException, InterruptedException {

    	// Algen global setup
//		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        // Start thread 1
        Future<ExperimentStats> future1 = executorService.submit(new ApplicationCallable(application));
        
        // Start thread 2
        Future<ExperimentStats> future2 = executorService.submit(new ApplicationCallable(application));
        
        // Wait thread 1
        logger.info("Stats1:"+future1.get());
        
        // Wait thread 2
        logger.info("Stats2:"+future2.get());
        
        return "Stats: "+future1.get()+" \n\r "+future2.get();
    }

    
    private class ApplicationCallable implements Callable<ExperimentStats> {
    	public String applicationName;
    	public ApplicationCallable(String application){
    		this.applicationName = application;
    	}
    	@Override
        public ExperimentStats call() throws Exception {
        	ExperimentStats stats = applicationService.executeBenchmark(applicationName);
            return stats;
        }
    }
}
