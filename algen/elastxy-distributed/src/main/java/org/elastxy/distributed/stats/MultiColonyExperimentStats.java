/*
 * java
 *
 * Created on 5 agosto 2007, 14.34
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.distributed.stats;

import java.util.List;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.stats.ExperimentStats;

/**
 * @author grossi
 */
public class MultiColonyExperimentStats extends ExperimentStats { // TODOB-2: manage two different types of ExperimentStats?
	// Common to SingleColonyExperimentStats
//	public Object target;
//    public double executionTimeMs;
//    public boolean targetReached;
    public int eras;
    public int totErasIdenticalFitnesses;
    public Solution bestMatch;
    public Long goalAccumulator;
    public List<Solution> otherBestMatches;

   
    // TODOA-2: create a specific ResultsRenderer
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Goal accumulator: "+goalAccumulator).append(Character.LINE_SEPARATOR);
        buf.append("Number of eras: "+eras).append(Character.LINE_SEPARATOR);
        buf.append("Total execution time (ms): "+executionTimeMs).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+totErasIdenticalFitnesses);
        buf.append("Other best matches:" + otherBestMatches).append(Character.LINE_SEPARATOR);
        return buf.toString();
    }
}