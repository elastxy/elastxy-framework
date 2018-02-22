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
package org.elastxy.distributed.dataprovider;

import java.util.List;

import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.domain.genetics.genotype.Allele;

/**
 * Transforms raw RDD<datatype> data into RDD<Allele> alleles.
 * 
 * RDD<Allele> cardinality should be enough to cover 
 * a single initial population: solutionsNumber + 50%
 * 
 * @author red
 *
 */
public interface DistributedGenomaProvider extends GenomaProvider {

	/**
	 * Collects strict number of Alleles for performing mutations 
	 * in subsequent executions.
	 * 
	 * In distributed context alleles are collected for a single Era on all colonies,
	 * and are translated by Driver in a Broadcast variable to spread
	 * them across nodes.
	 * 
	 * Number of alleles is based on algorithm parameters:
	 * era generations, solutions in a generation, reshuffle every tot eras...
	 * 
	 * @return
	 */
	public List<Allele> collectForMutation();
	
	
	/**
	 * Spreads Genoma to all colonies for allowing all genetic material
	 * to be shared within an Era.
	 * 
	 * In distributed context it means a repartition/coalesce of
	 * original data and subsequence genoma redefinition.
	 * 
	 * TODO3-4: Elitism: maintain best matches over reshuffle?
	 */
	public void spread();

}
