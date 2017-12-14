package it.red.algen.distributed.sandbox;

public class SparkJobConfig {

	public String masterURI = null; 			//e.g. "spark://192.168.1.101:7077";
	public String masterHost = null; 			//e.g. "192.168.1.101";
	public String sparkVersion = null; 			//e.g. "2.2.0";

	public String appName = null; 				//e.g. "MexApplication";
	public String appJar = null; 				//e.g. "c:/dev/workspaces/ws_scala/Scaligen/target/scala-2.11/Scaligen-assembly-1.0.jar";
	public String mainClass = null; 			//e.g. "it.red.algen.d.mex.MexApplication";
	
	public String log4jConfiguration = null;	//e.g. "c:/dev/spark-2.2.0-bin-hadoop2.7/conf";
	public String historyEventsEnabled = null; 	//e.g. "false";
	public String historyEventsDir = null; 		//e.g. "c:/tmp/sparktemp/eventLog";

}
