/*
 * IllegalSolutionException.java
 *
 * Created on 4 agosto 2007, 19.29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.engine.core;

/**
 * Checked exception to be managed in case of
 * problems during solutions growth.
 * 
 * TODO3-2: define better structure for validation rule and breaks
 * 
 * @author grossi
 */
public class IllegalSolutionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String legalCheck = "No legal check details."; // TODO0-8: i18n, i10n

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
