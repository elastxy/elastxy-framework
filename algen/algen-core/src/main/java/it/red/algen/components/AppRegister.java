package it.red.algen.components;

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
	 * Retrieves all application metadata
	 * @return
	 */
	public Map<String, AlgenApplication> findAll();

	
	/**
	 * Add a new application
	 * @return
	 */
	public Map<String, AlgenApplication> register();

	
	/**
	 * Remove an existing application from the register
	 * @return
	 */
	public Map<String, AlgenApplication> unregister();

}