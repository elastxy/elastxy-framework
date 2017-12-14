/*
 * EnvFactory.java
 *
 * Created on 5 agosto 2007, 15.24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.engine.factory;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.domain.experiment.Env;

/**
 *
 * @author grossi
 */
public interface EnvFactory {
	
	public void setup(AlgorithmContext context);
    
	/**
	 * Class responsible for providing a Target to the environment.
	 * @param targetBuilder
	 */
	public void setTargetBuilder(TargetBuilder targetBuilder);
	
	public Env create();
}
