package it.red.algen.conf;

/**
 * Exception thrown in case of any configuration, initialization, 
 * bootstrap, context problem encountered.
 * 
 * @author red
 *
 */
public class ConfigurationException extends RuntimeException {
	public ConfigurationException(String msg){
		super(msg);
	}
	public ConfigurationException(String msg, Throwable initCause){
		super(msg, initCause);
	}
}
