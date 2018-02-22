/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
