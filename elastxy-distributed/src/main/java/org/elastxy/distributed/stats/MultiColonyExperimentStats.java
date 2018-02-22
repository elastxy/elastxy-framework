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
package org.elastxy.distributed.stats;

import java.util.List;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.stats.ExperimentStats;

/**
 * 
 * TODO3-2: manage two different types of ExperimentStats?
 * 
 * @author grossi
 */
public class MultiColonyExperimentStats extends ExperimentStats {
	// Common to SingleColonyExperimentStats
//	public Object target;
//    public double executionTimeMs;
//    public boolean targetReached;
    public int eras;
    public int eons;
    public int totErasIdenticalFitnesses;
    public Long goalAccumulator;
    public List<Solution> otherBestMatches;

   
    // TODO1-2: ResultsRenderer: create a specific multicolony ResultsRenderer
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Goal accumulator: "+goalAccumulator).append(Character.LINE_SEPARATOR);
        buf.append("Number of eras: "+eras).append(Character.LINE_SEPARATOR);
        buf.append("Number of eons: "+eons).append(Character.LINE_SEPARATOR);
        buf.append("Total execution time (ms): "+executionTimeMs).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+totErasIdenticalFitnesses);
        buf.append("Other best matches:" + otherBestMatches).append(Character.LINE_SEPARATOR);
        return buf.toString();
    }
}
