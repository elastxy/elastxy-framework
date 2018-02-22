/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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

    public void newEon(MultiColonyEnv env);
}
