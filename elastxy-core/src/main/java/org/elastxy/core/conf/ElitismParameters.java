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
package org.elastxy.core.conf;

import java.io.Serializable;

/**
 * Elitism operator parameters, driving single- and multi- colony evolution.
 * 
 * @author red
 *
 */
public class ElitismParameters implements Serializable {
	
	/**
	 * Best matches are preserved between generations.
	 * 
	 * A number of singleColonyElitismNumber solutions are maintained into next generation 
	 * to preserve their (good) genes.
	 * 
	 * The same number of worst solutions are dropped, replaced by a copy of the best matches 
	 * thus participating in mutation and recombination.
	 */
	public boolean singleColonyElitism = 	DefaultConfiguration.DEFAULT_SINGLECOLONY_ELITISM_ENABLED;
	
	/**
	 * Perc of all generation solutions to presrve.
	 */
	public Double singleColonyElitismPerc = 	DefaultConfiguration.DEFAULT_SINGLECOLONY_ELITISM_PERC;
	
	/**
	 * Number of generation solutions to preserve.
	 */
	public Long singleColonyElitismNumber = 	DefaultConfiguration.DEFAULT_SINGLECOLONY_ELITISM_NUMBER;

	/**
	 * Best matches are preserved between eras.
	 * All multiColonyElitismNumber are reinserted into ALL colonies to spread their (good) genes around.
	 */
    public boolean multiColonyElitism = DefaultConfiguration.DEFAULT_MULTICOLONY_ELITISM_ENABLED;

    /**
     * Perc of elite solutions to take from all best matches and broadcast again around. 
     * Percentage is related to a single population number.
     */
	public Double multiColonyElitismPerc = 	DefaultConfiguration.DEFAULT_MULTICOLONY_ELITISM_PERC;
	
	/**
	 * Number of elite solutions to take from all best matches and broadcast again around.
	 */
	public Long multiColonyElitismNumber = 	DefaultConfiguration.DEFAULT_MULTICOLONY_ELITISM_NUMBER;

	/**
	 * If true, recombines or copies solutions as-is over generations/eras.
	 */
	public Boolean recombineElite = DefaultConfiguration.DEFAULT_RECOMBINE_ELITE;
	
}
