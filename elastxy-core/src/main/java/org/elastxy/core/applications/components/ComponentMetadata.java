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
package org.elastxy.core.applications.components;


/**
 * Metadata for a Component
 * 
 * TODO3-8: components as scripting passed online: to be carefully evaluated
 * 
 * @author red
 *
 */
public class ComponentMetadata {
	/**
	 * A Java class provided in the classpath (default).
	 */
	public static final String TYPE_JAVA = 		"java";
	
//	/**
//	 * A Scala script.
//	 */
//	public static final String TYPE_SCALA = 	"scala";
//	
//	/**
//	 * A Groovy script.
//	 */
//	public static final String TYPE_SCRIPT =	"groovy";
	
//	/**
//	 * Name of the component, to be chosen amongst AppComponents constants.
//	 */
//	public String name;

	/**
	 * Type of the component:
	 * "java" for a fully qualified Java class provided in the classpath
	 */
	public String type = TYPE_JAVA;
	
	/**
	 * Content of the metadata: e.g. Java fully qualifier name for TYPE_JAVA
	 */
	public String content;
	
	
	
	public String toString(){
		return String.format("ComponentMetadata[type=%s; content=%.50s]", type, content);
	}
}
