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
