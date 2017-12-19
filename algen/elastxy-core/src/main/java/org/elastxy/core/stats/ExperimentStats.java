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

import java.util.List;

import org.elastxy.core.domain.experiment.Population;

/**
 * @author grossi
 */
public class ExperimentStats  {
	// TODOA-8: fix generics
	// TODOM-2: typed to Target
	public Object target;
//    public String bestMatchRendering;
    public Population lastGeneration;
    public int generations;
    public long executionTimeMs; // total execution executionTimeMs in millis
    public int totIdenticalFitnesses;
    public boolean targetReached;
    public transient List<Population> generationHistory;
    
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + lastGeneration.bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Number of generations: "+generations).append(Character.LINE_SEPARATOR);
        buf.append("Total execution time (ms): "+executionTimeMs).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+totIdenticalFitnesses);
        return buf.toString();
    }
}