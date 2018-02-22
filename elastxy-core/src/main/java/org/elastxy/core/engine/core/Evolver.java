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
package org.elastxy.core.engine.core;

import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObservable;


/**
 * Generic abstraction representing the genetic algorithm evolution.
 * 
 * Implementors must provide evolution logics (local, distributed,
 * over many eras or in a single succession of generations, etc.).
 * 
 * Returns an ExperimentStats bean with main statistics
 * related to this evolution and collected by algorithm.
 * 
 * @author red
 *
 */
public interface Evolver extends EnvObservable {
    
	public void evolve();
	
    public ExperimentStats getStats();
    
    
}
