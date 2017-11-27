package it.red.algen.distributed;

public class SparkTaskConfig {

	public String masterURI = null; 			//e.g. "spark://192.168.1.101:7077";
	public String masterHost = null; 			//e.g. "192.168.1.101";
	public String sparkVersion = null; 			//e.g. "2.2.0";
	public String sparkHome = null; 			//e.g. "C:/dev/spark-2.2.0-bin-hadoop2.7";

	public String appName = null; 				//e.g. "MexApplication";
	public String appJarPath = null; 			//e.g. "file:///c:/dev/workspaces/ws_scala/Scaligen/target/scala-2.11/Scaligen-assembly-1.0.jar";
	public String mainClass = null; 			//e.g. "it.red.algen.d.mex.MexApplication";
	public String otherJarsPath = null; 		//e.g. "file:///c:/stuff/Scaligen-banana-1.0.jar;file:///c:/stuff/Scaligen-pera-1.0.jar";
	
//	public String log4jConfiguration = null;	//e.g. "c:/dev/spark-2.2.0-bin-hadoop2.7/conf";
//	public String historyEventsEnabled = null; 	//e.g. "false";
//	public String historyEventsDir = null; 		//e.g. "c:/tmp/sparktemp/eventLog";

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("masterURI = "+masterURI);
		sb.append("masterHost = "+masterHost);
		sb.append("sparkVersion = "+sparkVersion);
		sb.append("appName = "+appName);
		sb.append("appJarPath = "+appJarPath);
		sb.append("mainClass = "+mainClass);
		sb.append("otherJarsPath = "+otherJarsPath);
//		sb.append("log4jConfiguration = "+log4jConfiguration);
//		sb.append("historyEventsEnabled = "+historyEventsEnabled);
//		sb.append("historyEventsDir = "+historyEventsDir);
		return sb.toString();
	}
}
