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
package org.elastxy.core.context;

import java.io.Serializable;
import java.util.Locale;

import org.elastxy.core.conf.DefaultConfiguration;


/**
 * Context parameters for Algorithm execution.
 * 
 * All parameters and configurations related to execution environment
 * and client information.
 * 
 * @author red
 *
 */
public class RequestContext implements Serializable {

	/**
	 * Used to restrict permissions to request coming from Internet,
	 * or change behaviour, e.g. rendered information.
	 * 
	 * - true if request is coming from Internet (more restrictive, set on rev proxy)
	 * - false if request is coming from LAN (less restrictive)
	 * 
	 * Default: true
	 */
	public boolean webRequest = DefaultConfiguration.WEB_REQUEST; 
	
	
	/**
	 * User locale defined from request headers, or default to "en-GB".
	 */
	public Locale clientLocale = DefaultConfiguration.USER_LOCALE;


	public RequestContext(){
	}

	public RequestContext(Boolean webRequest){
		if(webRequest!=null) this.webRequest = webRequest;
	}
	
	public RequestContext(Boolean webRequest, Locale clientLocale){
		this(webRequest);
		if(clientLocale != null && DefaultConfiguration.AVAILABLE_LOCALES.contains(clientLocale)) this.clientLocale = clientLocale; 
	}
	

}
