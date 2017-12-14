/*
 * EnvFactory.java
 *
 * Created on 5 agosto 2007, 15.24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.distributed.engine.factory;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.engine.factory.EnvFactory;
import org.elastxy.distributed.experiment.MultiColonyEnv;

/**
 * Creates a MultiColonyEnv which holds distributed environment 
 * evolution info, such as eras, broadcast and accumulator variable.
 * 
 * Provides logics for building a new Environment
 * from an existing one of a previous era, updating all
 * attributes accordingly.
 * 
 * @author grossi
 */
public interface MultiColonyEnvFactory extends EnvFactory {
	
	public void setup(AlgorithmContext context);
    
	public MultiColonyEnv createEnv();

    public MultiColonyEnv newEra(MultiColonyEnv env);
}
