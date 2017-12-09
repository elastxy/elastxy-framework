/*
 * java
 *
 * Created on 5 agosto 2007, 14.34
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.stats;

import java.util.List;

import it.red.algen.domain.experiment.Population;

/**
 * @author grossi
 */
public class ExperimentStats  {
	// TODOA-8: fix generics
	// TODOM-2: typed to Target
	public Object target;
    public Population lastGeneration;
    public int generations;
    public double time;
    public int totIdenticalFitnesses;
    public boolean targetReached;
    public transient List<Population> generationHistory;
    
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + lastGeneration.bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Number of generations: "+generations).append(Character.LINE_SEPARATOR);
        buf.append("Total time (ms): "+time).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+totIdenticalFitnesses);
        return buf.toString();
    }
}
