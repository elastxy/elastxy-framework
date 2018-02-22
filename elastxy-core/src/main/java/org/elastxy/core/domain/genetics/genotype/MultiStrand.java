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
package org.elastxy.core.domain.genetics.genotype;
//package org.elastxy.core.core.domain.genetics;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Multiple strand fashion genotype, similar to a two-strand DNA (helix).
// * For every gene, chose the dominant allele.
// * 
// * TODO3-1: check if useful for improving performance
// *  
// * Position: "<strand>.<chromosome>.<gene>"
// * E.g. "0.0.0", "0.0.1", .. , "0.1.0", "5.4", .. , "M.N" 
// * 
// * @author red
// *
// */
//public class MultiStrand implements Genotype {
//	public List<Chromosome> strand1 = new ArrayList<Chromosome>();
//	public List<Chromosome> strand2 = new ArrayList<Chromosome>();
//
//	@Override
//	public String encode() {
//		throw new UnsupportedOperationException("NYI"); 
//	}
//
//	
//	@Override
//	public DoubleStrandGenotype copy() {
//		DoubleStrandGenotype result = new DoubleStrandGenotype();
//		result.strand1 = cloneStrand(strand1);
//		result.strand2 = cloneStrand(strand2);
//		return result;
//	}
//	
//	private List<Chromosome> cloneStrand(List<Chromosome> s){
//		return s.stream().map(c -> c.copy()).collect(Collectors.toList());
//	}
//
//
//	@Override
//	public List<String> getPositions() {
//		throw new UnsupportedOperationException("NYI");
//	}
//
//
//	@Override
//	public void replaceAllele(String position, Allele allele) {
//		throw new UnsupportedOperationException("NYI");
//	}
//
//	@Override
//	public void swapAllele(String position, Allele newAllele) {
//		throw new UnsupportedOperationException("NYI");
//	}
//}
