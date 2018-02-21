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
public class ClientFriendlyResults extends StandardExperimentResults {
	private static final long serialVersionUID = -9086588149674651491L;
	
	
	/**
	 * Fitness of found solution, in percentage (0-100).
	 */
	public double accuracy;
	
	
	/**
	 * General purpose result in binary format.
	 */
	public byte[] binaryResult;

	
	/**
	 * Generic purpose result as astring (e.g. an HTML/JSON response content).
	 */
	public String stringResult;
}
