package org.elastxy.core.applications.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.conf.ReadConfigSupport;
import org.elastxy.core.support.JSONSupport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

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
@Component
public class ClasspathRegister implements AppRegister {
	private static final Logger logger = Logger.getLogger(ClasspathRegister.class);

	@Override
	public ApplicationMetadata find(String applicationName){
		String classpathResource = JSONSupport.checkClasspathResource(applicationName, "application.json");
		ApplicationMetadata result;
		try {
			result = (ApplicationMetadata)JSONSupport.readJSON(classpathResource, ApplicationMetadata.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return result;
	}
	
	/**
	 * Scans classpath for finding all applications directory
	 * (those with an application.json metadata file inside)
	 */
	@Override
	public Map<String, ApplicationMetadata> findAll() {
		
		// Finding all candidate resources
		Resource[] resources = findClasspathResources();
		
		// Deserialize applications metadata
		Map<String, ApplicationMetadata> result = new HashMap<String, ApplicationMetadata>();
		for (Resource classpathResource : resources){
			ApplicationMetadata applicationMetadata;
			try {
				applicationMetadata = (ApplicationMetadata)JSONSupport.readJSON(classpathResource.getInputStream(), ApplicationMetadata.class);
				result.put(applicationMetadata.appName, applicationMetadata);
			} catch (Throwable e) {
				String msg = "Error while getting classpath resource "+classpathResource.getFilename()+". Ex: "+e;
				logger.error(msg, e);
			}
		}
		return result;
	}

	private Resource[] findClasspathResources() {
		ClassLoader cl = getClass().getClassLoader(); 
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		Resource[] resources;
		try {
			resources = resolver.getResources("classpath*:*/**/application.json");
		} catch (IOException e) {
			String msg = "Error while searching for /**/application.json in classpath. Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return resources;
	}

	@Override
	public Map<String, ApplicationMetadata> register() {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public Map<String, ApplicationMetadata> unregister() {
		throw new UnsupportedOperationException("NYI");
	}

}
