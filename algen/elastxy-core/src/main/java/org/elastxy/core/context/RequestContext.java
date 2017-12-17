package org.elastxy.core.context;

import java.util.Locale;

import org.elastxy.core.conf.DefaultConfiguration;

public class RequestContext {

	/**
	 * Used to restrict permissions to request coming from Internet,
	 * or change behaviour, e.g. rendered information.
	 * 
	 * - true if request is coming from Internet (more restrictive, set on rev proxy)
	 * - false if request is coming from LAN (less restrictive)
	 * 
	 * Default: true
	 */
	public transient boolean webRequest = DefaultConfiguration.WEB_REQUEST; 
	
	
	/**
	 * User locale defined from request headers, or default to "en-GB".
	 */
	public transient Locale clientLocale = DefaultConfiguration.USER_LOCALE;

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
