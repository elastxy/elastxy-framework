package it.red.algen.context;

/**
 * Exception thrown in case of any configuration, initialization, 
 * context problem encountered.
 * 
 * @author red
 *
 */
public class ContextException extends RuntimeException {
	public ContextException(String msg){
		super(msg);
	}
	public ContextException(String msg, Throwable initCause){
		super(msg, initCause);
	}
}
