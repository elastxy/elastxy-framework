package it.red.algen.distributed;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.data.EnvFactory;
import it.red.algen.expressions.context.ExprBenchmark;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;

@Component
public class DistributedTask {
	private static Logger logger = LoggerFactory.getLogger(DistributedTask.class);

	
	@SuppressWarnings("unused")
	@Autowired
	private JavaSparkContext sparkContext;
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private EnvFactory envFactory;
	
	@Autowired
	private ExprBenchmark exprBenchmark;
		
	private @Autowired AutowireCapableBeanFactory beanFactory;
    
	
    public String run() throws ExecutionException, InterruptedException {

    	// Algen global setup
//		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());
		AlgorithmContext context = exprBenchmark.build();

		
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // Start thread 1
        Future<ExperimentStats> future1 = executorService.submit(new Callable<ExperimentStats>() {
            @Override
            public ExperimentStats call() throws Exception {
        		contextSupplier.init(context);
        		Experiment e = new Experiment(envFactory);
        		beanFactory.autowireBean(e);
                e.run();
                return e.getStats();
            }
        });
        // Start thread 2
        Future<ExperimentStats> future2 = executorService.submit(new Callable<ExperimentStats>() {
            @Override
            public ExperimentStats call() throws Exception {
        		contextSupplier.init(context);
        		Experiment e = new Experiment(envFactory);
        		beanFactory.autowireBean(e);
                e.run();
                return e.getStats();
            }
        });
        // Wait thread 1
        logger.info("Stats1:"+future1.get());
        // Wait thread 2
        logger.info("Stats2:"+future2.get());
        
        contextSupplier.destroy();
        
        return "Stats: "+future1.get()+" \n\r "+future2.get();
    }
	
}
