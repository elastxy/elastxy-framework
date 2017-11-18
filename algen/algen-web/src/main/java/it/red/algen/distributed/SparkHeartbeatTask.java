package it.red.algen.distributed;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import scala.Tuple2;

@Component
public class SparkHeartbeatTask {
	private static Logger logger = LoggerFactory.getLogger(SparkHeartbeatTask.class);

	
	public String runSingle(JavaSparkContext sc, String inputFilePath) {
//	    final StringBuffer buf = new StringBuffer();
//	    final List<String> list = new ArrayList<String>();

	    JavaPairRDD<String, Integer> wordCount = sc.textFile(inputFilePath)
	        .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
	        .mapToPair(word -> new Tuple2<>(word, 1))
	        .reduceByKey((a, b) -> a + b);
	    
//	        .foreach(res -> logger.info(String.format("Word [%s] count [%d].%n", res._1(), res._2)));
//	        .foreach(res -> write(buf, list, String.format("Word [%s] count [%d].%n", res._1(), res._2)));
	    
//	    String result = wordCount.toDebugString();
	    
//	    wordCount.collect().stream(tuple -> write(buf, list, String.format("Word [%s] count [%d].%n", tuple._1, tuple_2)));
	    Stream<Tuple2<String,Integer>> s1 = wordCount.collect().stream();
	    Stream<String> s2 = s1.map(tuple -> String.format("Word [%s] count [%d].%n", tuple._1(), tuple._2));
//	    s2.forEach(SparkHeartbeatTask::write);
//	    s2.forEach(action);
	    
//	    String res = buf.toString();
//	    logger.info(res);
//	    return "BUF=\n"+res+"\nLIST=\n"+list.toString();
	    
	    return Arrays.toString(s2.toArray());
	}
	

}
