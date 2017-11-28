package it.red.algen.applications.components.factory;

import org.apache.log4j.Logger;

import it.red.algen.applications.components.AlgenApplication;
import it.red.algen.applications.components.AppComponents;
import it.red.algen.applications.components.AppComponentsBuilder;
import it.red.algen.applications.components.AppComponentsLocator;
import it.red.algen.applications.components.AppRegister;

/**
 * Builds up applications component and makes them available,
 * starting from a application register.
 * 
 * NO SPRING Version to be used where Spring is missing
 * 
 * @author red
 */
public class AppBootstrapRaw {
	private static Logger logger = Logger.getLogger(AppBootstrapRaw.class);

	
	/**
	 * Starts an Algen registered application
	 */
	public AppComponentsLocator boot(String applicationName){
		logger.info("Bootstrapping AliGen.D..");
		
		// Gets all registered apps from Register
		if(logger.isDebugEnabled()) logger.debug("Finding registered application '"+applicationName+"'");
		AppRegister register = new ClasspathRegisterRaw();
		AlgenApplication app = register.find(applicationName);
		if(logger.isDebugEnabled()) logger.debug("Application found: "+app);
		
		
		// Build progressively all the applications
		if(logger.isDebugEnabled()) logger.debug(">> Bootstrapping application '"+applicationName+"'");
		
		if(logger.isDebugEnabled()) logger.debug("   Building components..");
		AppComponentsBuilder builder = new AppComponentsBuilder();
		AppComponents appComponents = builder.construct(app);
	
		if(logger.isDebugEnabled()) logger.debug("   Wiring components..");
		appComponents = builder.wire(appComponents);
			
		if(logger.isDebugEnabled()) logger.debug("   Initializing components..");
		appComponents = builder.init(appComponents);
		
		AppComponentsLocator locator = new AppComponentsLocator();
		locator.put(applicationName, appComponents);
		if(logger.isDebugEnabled()) logger.debug("   Welcome to '"+applicationName+"' application! <!!!>o");
		
		if(logger.isDebugEnabled()) logger.debug("Bootstrap AliGen.D DONE.");
		return locator;
	}
	
	
}
