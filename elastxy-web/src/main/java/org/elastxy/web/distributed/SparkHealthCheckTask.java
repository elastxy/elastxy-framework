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
package org.elastxy.web.distributed;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class SparkHealthCheckTask implements Serializable {
	
	private static final long serialVersionUID = -3212690278671841223L;

	
	public static String run(JavaSparkContext sc, String inputFilePath) {
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
	    List<Tuple2<String,Integer>> wordCountList = wordCount.collect();
	    Stream<Tuple2<String,Integer>> s1 = wordCountList.stream();
	    Stream<String> s2 = s1.map(tuple -> String.format("Word [%s] count [%d].%n", tuple._1(), tuple._2));
//	    s2.forEach(SparkHeartbeatTask::write);
//	    s2.forEach(action);
	    
//	    String res = buf.toString();
//	    logger.info(res);
//	    return "BUF=\n"+res+"\nLIST=\n"+list.toString();
	    
	    return Arrays.toString(s2.toArray());
	}
	

}
