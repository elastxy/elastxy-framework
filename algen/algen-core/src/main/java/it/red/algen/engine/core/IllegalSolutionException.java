/*
 * IllegalSolutionException.java
 *
 * Created on 4 agosto 2007, 19.29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.engine.core;

/**
 * Checked exception to be managed in case of
 * problems during solutions growth.
 * 
 * TODOB: define better structure for validation rule and breaks
 * 
 * @author grossi
 */
public class IllegalSolutionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String legalCheck = "No legal check details."; // TODOM: i18n, i10n

	public IllegalSolutionException(String msg) {
        super(msg);
    }

	public IllegalSolutionException(String msg, String legalCheck) {
        super(msg);
        this.legalCheck = legalCheck;
    }
	
	
	/**
	 * Legal check hosts details on problem occourred.
	 * @return
	 */
	public String getLegalCheck(){
		return legalCheck;
	}
}
