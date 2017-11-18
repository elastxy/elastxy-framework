package it.red.algen.applications.components.factory;

import org.apache.log4j.Logger;

import it.red.algen.applications.components.AlgenApplication;
import it.red.algen.applications.components.AppComponents;
import it.red.algen.applications.components.AppComponentsBuilder;
import it.red.algen.applications.components.AppComponentsLocator;
import it.red.algen.applications.components.AppRegister;
import it.red.algen.applications.components.ClasspathRegister;

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
		logger.info("Bootstrapping AliGen..");
		
		// Gets all registered apps from Register
		logger.info("Finding registered application '"+applicationName+"'");
		AppRegister register = new ClasspathRegister();
		AlgenApplication app = register.find(applicationName);
		logger.info("Application found: "+app);
		
		
		// Build progressively all the applications
		logger.info(">> Bootstrapping application '"+applicationName+"'");
		
		logger.info("   Building components..");
		AppComponentsBuilder builder = new AppComponentsBuilder();
		AppComponents appComponents = builder.construct(app);
	
		logger.info("   Wiring components..");
		appComponents = builder.wire(appComponents);
			
		logger.info("   Initializing components..");
		appComponents = builder.init(appComponents);
		
		AppComponentsLocator locator = new AppComponentsLocator();
		locator.put(applicationName, appComponents);
		logger.info("   Welcome to '"+applicationName+"' application! <!!!>o");
		
		logger.info("Bootstrap AliGen.D DONE.");
		return locator;
	}
	
	
}
