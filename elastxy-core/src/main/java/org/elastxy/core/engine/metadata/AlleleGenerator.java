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
package org.elastxy.core.engine.metadata;

import java.util.List;

import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;

/**
 * Generates a new Allele given metadata
 * @author red
 *
 */
public interface AlleleGenerator {
	
	
	/**
	 * Setup AlleleGenerator with access to Genoma.
	 * 
	 * @param Genoma
	 */
	public void setup(Genoma genoma);
	
	
	/**
	 * Generate a random allele
	 * @param metadata
	 * @return
	 */
	public <T> Allele<T> generateRandom(GeneMetadata metadata);
	
	/**
	 * Generate a new allele with specific value.
	 * 
	 * @param metadata
	 * @return
	 */
	public <T> Allele<T> generateFromValue(T value);
	

	/*
	 * Generates a new allele, excluding some specific values.
	 * 
	 * Useful for problems where every gene must have a different allele from a predefined set
	 * 
	 */
	public <T> Allele<T> generateExclusive(GeneMetadata metadata, List<T> exclusions);
	

	/**
	 * Generate the Allele with the first value available in metadata values
	 * @param metadata
	 * @return
	 */
	public <T> Allele<T> generateFirst(GeneMetadata metadata);
}
