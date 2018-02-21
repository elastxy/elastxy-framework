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
