/*
 * Stats.java
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
    public List<Population> _generationHistory;
}
