package org.elastxy.core.applications;

/**
 * Generic error encountered during algorithm execution within an Application.
 * 
 * @author red
 *
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ApplicationException(String msg) {
        super(msg);
    }

	public ApplicationException(String msg, Throwable t) {
        super(msg, t);
    }
}
