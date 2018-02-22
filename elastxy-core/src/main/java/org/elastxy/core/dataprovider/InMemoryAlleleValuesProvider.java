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
package org.elastxy.core.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elastxy.core.domain.genetics.genotype.Allele;

/**
 * A simple Allele provider for hosting list of Alleles.
 * 
 * It can be loaded in a GenomaProvider.
 * 
 * @author red
 *
 */
public class InMemoryAlleleValuesProvider implements AlleleValuesProvider {
	
	
	
	/**
	 * Used when a single list is enough, for efficiency.
	 */
	private List<Allele> singleListAlleles = new ArrayList<Allele>();

	
	/**
	 * Used when list of alleles is specific to a provider.
	 */
	private Map<String,List<Allele>> allelesByProvider = new HashMap<String,List<Allele>>();
	

	/**
	 * Return 1 if a single list of alleles is used,
	 * otherwise the number of providers mapped.
	 */
	@Override
	public int countProviders() {
		int result = allelesByProvider.size();
		return result==0 ? 1 : result;
	}
	
	@Override
	public List<Allele> getAlleles(String provider) {
		return allelesByProvider.get(provider);
	}

	
	@Override
	public void insertAlleles(String provider, List<Allele> alleles) {
		allelesByProvider.put(provider, alleles);
	}

	
	@Override
	public List<Allele> getAlleles() {
		return singleListAlleles;
	}

	
	@Override
	public void insertAlleles(List<Allele> alleles) {
		singleListAlleles = alleles;
	}

}
