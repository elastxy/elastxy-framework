package it.red.algen.distributed;

import java.io.File;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.expressions.context.ExprBenchmark;
import it.red.algen.expressions.engine.ExprEnvFactory;
import it.red.algen.stats.Experiment;

@Component
public class DistributedService {
	
    @Autowired
    private SparkSession sparkSession;


	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private ExprEnvFactory exprEnvFactory;
	@Autowired
	private ExprBenchmark exprBenchmark;
		
	private @Autowired AutowireCapableBeanFactory beanFactory;
    
    public void run(){

		System.setProperty("datadir", new File("C:\\tmp\\algendata").getAbsolutePath());

		AlgorithmContext context = exprBenchmark.build();
		contextSupplier.init(context);
		
		Experiment e = new Experiment(exprEnvFactory);
		beanFactory.autowireBean(e);
		
        e.run();
        
        contextSupplier.destroy();
    }
	
}
