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

import java.io.Serializable;

public class StandardExperimentResults implements Serializable {
	private static final long serialVersionUID = -3136599307752592810L;

	/**
	 * String representation of target, if needed.
	 */
	public String target;
	
	/**
	 * True if goal has been reached.
	 */
    public boolean goalReached; // TODO2-2: more details of how has finished
    
    
    /**
     * Total number of iterations (generations) needed 
     * for finishing the experiment.
     */
	public long iterationsNumber;

	
	/**
	 * Total experiment execution time in ms.
	 */
	public long totalExecutionTimeMs;

	
	/**
	 * Additional info to be eventually reported.
	 */
    public String notes;
	
}
