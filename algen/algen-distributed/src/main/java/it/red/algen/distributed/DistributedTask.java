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

import it.red.algen.components.AppComponentsLocator;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextBuilder;
import it.red.algen.context.ContextSupplier;
import it.red.algen.stats.Experiment;
import it.red.algen.stats.ExperimentStats;

@Component
public class DistributedTask {
	private static Logger logger = LoggerFactory.getLogger(DistributedTask.class);

	
	@SuppressWarnings("unused")
	@Autowired
	private JavaSparkContext sparkContext;
	
	@Autowired private ContextSupplier contextSupplier;

	@Autowired private ContextBuilder benchmarkContextBuilder;
	
	@Autowired private AutowireCapableBeanFactory beanFactory;

	@Autowired private AppComponentsLocator appComponentsLocator;
    
	
    public String run() throws ExecutionException, InterruptedException {

    	// Algen global setup
//		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());
		final String applicationName = "expressions";

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // Start thread 1
        Future<ExperimentStats> future1 = executorService.submit(new Callable<ExperimentStats>() {
            @Override
            public ExperimentStats call() throws Exception {
            	AlgorithmContext context = benchmarkContextBuilder.build(applicationName, true);
        		context.application.name = applicationName;
        		setupContext(context);
        		contextSupplier.init(context);
        		Experiment e = new Experiment(context.application.envFactory);
        		beanFactory.autowireBean(e);
                e.run();
                ExperimentStats stats = e.getStats();
                contextSupplier.destroy();
                return stats;
            }
        });
        // Start thread 2
        Future<ExperimentStats> future2 = executorService.submit(new Callable<ExperimentStats>() {
            @Override
            public ExperimentStats call() throws Exception {
            	AlgorithmContext context = benchmarkContextBuilder.build(applicationName, true);
        		context.application.name = applicationName;
        		setupContext(context);
        		contextSupplier.init(context);
        		Experiment e = new Experiment(context.application.envFactory);
        		beanFactory.autowireBean(e);
                e.run();
                ExperimentStats stats = e.getStats();
                contextSupplier.destroy();
                return stats;
            }
        });
        // Wait thread 1
        logger.info("Stats1:"+future1.get());
        // Wait thread 2
        logger.info("Stats2:"+future2.get());
        
        contextSupplier.destroy();
        
        return "Stats: "+future1.get()+" \n\r "+future2.get();
    }
	
	private void setupContext(AlgorithmContext context) {
		context.application = appComponentsLocator.get(context.application.name);
		if(context.application.datasetProvider!=null) context.application.datasetProvider.setup(context);
		context.application.genomaProvider.setup(context);
		context.application.selector.setup(context);
		context.application.envFactory.setup(context);
	}

}
