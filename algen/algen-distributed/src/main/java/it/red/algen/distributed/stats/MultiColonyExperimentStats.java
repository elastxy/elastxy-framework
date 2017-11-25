/*
 * java
 *
 * Created on 5 agosto 2007, 14.34
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.distributed.stats;

import java.util.List;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.stats.ExperimentStats;

/**
 * @author grossi
 */
public class MultiColonyExperimentStats extends ExperimentStats { // TODOD: manage two different types
	// TODOM: typed to Target
	public Object target;
    public int eras;
    public double time;
    public int totErasIdenticalFitnesses;
    public boolean targetReached;
    public Solution bestMatch;
    public Long goalAccumulator;
    public List<Solution> otherBestMatches;
    
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Goal accumulator: "+goalAccumulator).append(Character.LINE_SEPARATOR);
        buf.append("Number of eras: "+eras).append(Character.LINE_SEPARATOR);
        buf.append("Total time (ms): "+time).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+totErasIdenticalFitnesses);
        buf.append("Other best matches:" + otherBestMatches).append(Character.LINE_SEPARATOR);
        return buf.toString();
    }
}
