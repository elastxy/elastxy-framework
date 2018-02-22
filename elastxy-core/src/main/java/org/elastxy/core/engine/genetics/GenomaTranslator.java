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
package org.elastxy.core.engine.genetics;

import org.elastxy.core.domain.genetics.genotype.Gene;

/**
 * Encode/Decode genoma parts
 * @author red
 *
 */
public interface GenomaTranslator {

	/**
	 * Encode a gene to a specific serializable format
	 * @return
	 */
	public Object encode(Gene gene);
	
	
	/**
	 * Decode a specific serializable format to a gene
	 * @return
	 */
	public Gene decode(Object serializedGene);
	

}
