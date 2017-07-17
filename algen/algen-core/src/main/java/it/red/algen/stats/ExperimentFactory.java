/*
 * ExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.28
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.stats;

import it.red.algen.Target;
import it.red.algen.conf.AlgorithmContext;

/**
 *
 * @author grossi
 */
public interface ExperimentFactory {
    
    public Experiment create(AlgorithmContext context, Target target);
    
}
