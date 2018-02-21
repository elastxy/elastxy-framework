package org.elastxy.core.applications.components;

import java.util.Map;

/**
 * Represents an Application metadata definition source:
 * where to physically retrieve the configurations (classpath, 
 * file-system, database..).
 * 
 * @author red
 *
 */
public interface AppRegister {
	

	/**
	 * Retrieves an application metadata
	 * @return
	 */
	public ApplicationMetadata find(String applicationName);

	/**
	 * Retrieves all application metadata
	 * @return
	 */
	public Map<String, ApplicationMetadata> findAll();

	
	/**
	 * Add a new application
	 * @return
	 */
	public Map<String, ApplicationMetadata> register();

	
	/**
	 * Remove an existing application from the register
	 * @return
	 */
	public Map<String, ApplicationMetadata> unregister();

}
