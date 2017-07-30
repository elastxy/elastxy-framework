package it.red.algen.distributed;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scala.Tuple2;

@Component
public class SparkHeartbeatTask {
	private static Logger logger = LoggerFactory.getLogger(SparkHeartbeatTask.class);

	@Autowired
	private JavaSparkContext sparkContext;
	
	
	public String runSingle(String inputFilePath) {
	    StringBuffer buf = new StringBuffer();

	    sparkContext.textFile(inputFilePath)
	        .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
	        .mapToPair(word -> new Tuple2<>(word, 1))
	        .reduceByKey((a, b) -> a + b)
	        .foreach(res -> logger.info(String.format("Word [%s] count [%d].", res._1(), res._2)));
//	        .foreach(res -> write(buf, String.format("Word [%s] count [%d].", res._1(), res._2)));
	    
	    String res = buf.toString();
	    logger.info(res);
	    return res;
	}
	

    public String runConcurrent(String textPath1, String textPath2) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // Start thread 1
        Future<Long> future1 = executorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                JavaRDD<String> file1 = sparkContext.textFile(textPath1);
                return file1.count();
            }
        });
        // Start thread 2
        Future<Long> future2 = executorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                JavaRDD<String> file2 = sparkContext.textFile(textPath2);
                return file2.count();
            }
        });
        // Wait thread 1
        logger.info("File1:"+future1.get());
        // Wait thread 2
        logger.info("File2:"+future2.get());
        
        return "Counts: "+future1.get()+" & "+future2.get();
    }
    
    
	private void write(StringBuffer buf, String msg){
		buf.append(msg);
		logger.info(msg);
	}
}
