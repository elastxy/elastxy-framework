/*
 * IllegalSolutionException.java
 *
 * Created on 4 agosto 2007, 19.29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.engine;

/**
 * TODOB: define better structure for validation rule and breaks
 * 
 * @author grossi
 */
public class IllegalSolutionException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalSolutionException(String msg) {
        super(msg);
    }
}
