package it.red.algen.applications.components.factory;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import it.red.algen.applications.components.AlgenApplication;
import it.red.algen.applications.components.AppRegister;
import it.red.algen.conf.ConfigurationException;
import it.red.algen.conf.ReadConfigSupport;

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
	public AlgenApplication find(String applicationName){
		String classpathResource = "/"+applicationName+"/application.json";
		AlgenApplication result;
		try {
			result = (AlgenApplication)ReadConfigSupport.readJSON(classpathResource, AlgenApplication.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return result;
	}

	@Override
	public Map<String, AlgenApplication> findAll() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}

	@Override
	public Map<String, AlgenApplication> register() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}

	@Override
	public Map<String, AlgenApplication> unregister() {
		throw new UnsupportedOperationException("Not supported on local node.");
	}
}
