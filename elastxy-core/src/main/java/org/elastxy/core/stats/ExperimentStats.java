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

import java.util.List;

import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;

/**
 * @author grossi
 */
public class ExperimentStats  {
	// TODO1-8: fix generics
	// TODO3-2: typed to Target
	public Object target;
//    public String bestMatchRendering;
    public Solution bestMatch;
    public int generations;
    public long executionTimeMs; // total execution executionTimeMs in millis
    public int totIdenticalFitnesses;
    public boolean targetReached;
    public transient Population lastGeneration;
    public transient List<Population> generationHistory;
    
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Other best matches:" + lastGeneration.bestMatches==null?0:lastGeneration.bestMatches.size()).append(Character.LINE_SEPARATOR);
        buf.append("Number of generations: "+generations).append(Character.LINE_SEPARATOR);
        buf.append("Total execution time (ms): "+executionTimeMs).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+totIdenticalFitnesses);
        return buf.toString();
    }
}
