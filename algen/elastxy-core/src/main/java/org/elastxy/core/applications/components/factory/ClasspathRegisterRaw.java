package org.elastxy.core.applications.components.factory;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elastxy.core.applications.components.ApplicationMetadata;
import org.elastxy.core.applications.components.AppRegister;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.conf.ReadConfigSupport;

/**
 * Retrieves Application metadata from classpath.
 * 
 * Default location is:
 * 
 * classpath:{applicationName}
 * 
 * @author red
 *
 */
public class ClasspathRegisterRaw implements AppRegister {
	private static final Logger logger = Logger.getLogger(ClasspathRegisterRaw.class);

	@Override
	public ApplicationMetadata find(String applicationName){
		String classpathResource = "/"+applicationName+"/application.json";
		ApplicationMetadata result;
		try {
			result = (ApplicationMetadata)ReadConfigSupport.readJSON(classpathResource, ApplicationMetadata.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return result;
	}

	@Override
	public Map<String, ApplicationMetadata> findAll() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}

	@Override
	public Map<String, ApplicationMetadata> register() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}

	@Override
	public Map<String, ApplicationMetadata> unregister() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}
}
