package it.red.algen.applications;

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
}
