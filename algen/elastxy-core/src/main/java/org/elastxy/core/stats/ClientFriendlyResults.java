/*
 * java
 *
 * Created on 5 agosto 2007, 14.34
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.stats;

import java.io.Serializable;

/**
 * Only main friendly properties are shown (e.g. for a web target audience).
 * 
 * No details on algorithm is given here, just the minimum set of information.
 * 
 * Some generic usage properties are provided, but all of them are optional
 * (e.g. binary).
 * 
 * @author grossi
 */
public class ClientFriendlyResults implements Serializable {
	private static final long serialVersionUID = -3136599307752592810L;
	
	
	public String target;
	
    public boolean goalReached; // TODOM-2: more details of how has finished
    public double accuracy; // fitness
    public String bestMatch; // client specific rendering (html, jpeg, ...)
	
    public long iterationsNumber;
    public long totalExecutionTimeMs;

    public String notes; // additional info to be reported
    

	/**
	 * Binary result
	 */
	public byte[] binaryResult;

	
	/**
	 * Generic String result (e.g. an HTML/JSON response content)
	 */
	public String stringResult;
}
