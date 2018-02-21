package org.elastxy.core.engine.core;

/**
 * Generic error encountered during algorithm execution.
 * 
 * @author red
 *
 */
public class AlgorithmException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlgorithmException(String msg) {
        super(msg);
    }

	public AlgorithmException(String msg, Throwable t) {
        super(msg, t);
    }
}
