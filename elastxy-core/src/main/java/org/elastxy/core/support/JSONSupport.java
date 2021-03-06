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
package org.elastxy.core.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.conf.ReadConfigSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSupport {
	private static final String DEFAULT_PATH = "app";
	
	public static String checkClasspathResource(String applicationName, String fileName){
		String classpathResource = "/"+applicationName+"/"+fileName;
		
		// Check resource presence
		URL u = JSONSupport.class.getResource(classpathResource);
		if (u == null) {
			classpathResource = "/"+DEFAULT_PATH+"/"+fileName;
		}
		u = JSONSupport.class.getResource(classpathResource);
		if (u == null) {
			throw new ConfigurationException("Resource not found in classpath: "+classpathResource);
		}
		return classpathResource;
	}

	public static Object readJSON(String classpathResource, Class type) throws IOException {
		return readJSON(ReadConfigSupport.class.getResourceAsStream(classpathResource), type);
	}
	
	public static Object readJSON(InputStream inputStream, Class type) throws IOException {
		return readJSON(inputStream, type, false);
	}

	public static Object readJSON(InputStream inputStream, Class type, boolean defaultTyping) throws IOException {
    	ObjectMapper om = new ObjectMapper();
    	if(defaultTyping) om.enableDefaultTyping();
		Object result = om.readValue(inputStream, type);
		return result;
	}

	public static Object readJSONString(String inputString, Class type) throws IOException {
		return readJSONString(inputString, type, false);
	}

	public static Object readJSONString(String inputString, Class type, boolean enableDefaultTyping) throws IOException {
    	ObjectMapper om = new ObjectMapper();
    	if(enableDefaultTyping) om.enableDefaultTyping();
		Object result = om.readValue(inputString, type);
		return result;
	}

	public static String writeJSONString(Object inputObject) throws IOException {
		return writeJSONString(inputObject, false);
	}

	public static String writeJSONString(Object inputObject, boolean enableDefaultTyping) throws IOException {
    	ObjectMapper om = new ObjectMapper();
    	if(enableDefaultTyping) om.enableDefaultTyping();
		String result = om.writeValueAsString(inputObject);
		return result;
	}

	public static void writeJSONObject(File outputFile, Object inputObject) throws IOException {
    	ObjectMapper om = new ObjectMapper();
    	om.enableDefaultTyping();
		om.writeValue(outputFile, inputObject);
	}
}
