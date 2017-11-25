/*
 * EnvFactory.java
 *
 * Created on 5 agosto 2007, 15.24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.distributed.engine.factory;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.distributed.experiment.MultiColonyEnv;
import it.red.algen.engine.factory.EnvFactory;

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
public interface MultiColonyEnvFactory extends EnvFactory { // TODOD: type of MultiColonyEnvFactory. decouple from this iterface
	
	public void setup(AlgorithmContext context);
    
	public MultiColonyEnv createEnv();

    public MultiColonyEnv newEra(MultiColonyEnv env);
}
