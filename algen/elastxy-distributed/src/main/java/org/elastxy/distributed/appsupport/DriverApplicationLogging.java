package org.elastxy.distributed.appsupport;

import org.apache.log4j.Logger;

public class DriverApplicationLogging {
	private Logger logger;
	
	public DriverApplicationLogging(Logger logger){
		this.logger = logger;
	}
	
//	stream = new BufferedWriter(new FileWriter("C:\\tmp\\mylog.log"));

    public void info(String message) throws Exception{
    	System.out.println(message);
    	if(logger!=null) logger.info(message);
//    	if(logger2!=null) logger2.info(message);
//    	stream.write(message);
//    	stream.newLine();
    }

    public void error(String message) throws Exception{
    	System.out.println(message);
    	if(logger!=null) logger.error(message);
//    	if(logger2!=null) logger2.error(message);
//    	stream.write(message);
//    	stream.newLine();
    }

    public void error(String message, Throwable exception) throws Exception{
    	System.out.println(message);
    	exception.printStackTrace();
    	if(logger!=null) logger.error(message, exception);
//    	if(logger2!=null) logger2.error(message, exception);
//    	stream.write(message+" "+exception.toString());
//    	stream.newLine();
    }
	
}
