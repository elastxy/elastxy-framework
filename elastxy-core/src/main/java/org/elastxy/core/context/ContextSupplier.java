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
package org.elastxy.core.context;

/**
 * Implementing classes must ensure the clients to get current
 * algorithm context during the whole experiment.
 * 
 * Initialization and finalization steps may also be provided.
 * 
 * Algorithm context can be built by a {@link ContextBuilder}
 * 
 * @author red
 *
 */
public interface ContextSupplier {

	public void init(AlgorithmContext context);

	public AlgorithmContext getContext();

	public void destroy();
	
}
