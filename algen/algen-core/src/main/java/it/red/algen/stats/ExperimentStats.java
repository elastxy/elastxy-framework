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

import it.red.algen.domain.Population;

/**
 *TODOM: togliere carattere _
 * @author grossi
 */
public class ExperimentStats  {
	// TODOM: tipizzato a Target
	public Object _target;
    public Population _lastGeneration;
    public int _generations;
    public double _time;
    public int _totIdenticalFitnesses;
    public boolean targetReached;
    public transient List<Population> _generationHistory;
    
    public String toString(){
    	StringBuffer buf = new StringBuffer();
        buf.append("##################### STATS #####################").append(Character.LINE_SEPARATOR);
        buf.append("Best match:" + _lastGeneration.bestMatch).append(Character.LINE_SEPARATOR);
        buf.append("Number of generations: "+_generations).append(Character.LINE_SEPARATOR);
        buf.append("Total time (sec): "+_time).append(Character.LINE_SEPARATOR);
        buf.append("Total generations with same fitness: "+_totIdenticalFitnesses);
        return buf.toString();
    }
}
