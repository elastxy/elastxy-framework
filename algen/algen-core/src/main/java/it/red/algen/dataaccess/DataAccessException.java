package it.red.algen.dataaccess;

/**
 * Exception thrown in case of any data access exception.
 * 
 * @author red
 *
 */
public class DataAccessException extends RuntimeException {
	public DataAccessException(String msg){
		super(msg);
	}
	public DataAccessException(String msg, Throwable initCause){
		super(msg, initCause);
	}
}
