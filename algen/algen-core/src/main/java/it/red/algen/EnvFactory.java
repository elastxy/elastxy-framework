/*
 * EnvFactory.java
 *
 * Created on 5 agosto 2007, 15.24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen;

import it.red.algen.context.AlgorithmContext;

/**
 *
 * @author grossi
 */
public interface EnvFactory {
    
	public Env create(AlgorithmContext context, Target target);
}
