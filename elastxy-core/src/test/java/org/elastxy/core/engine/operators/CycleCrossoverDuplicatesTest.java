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
//package org.elastxy.core.engine.operators;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.commons.math3.genetics.AbstractListChromosome;
//import org.apache.commons.math3.genetics.Chromosome;
//import org.apache.commons.math3.genetics.ChromosomePair;
//import org.apache.commons.math3.genetics.CycleCrossover;
//import org.apache.commons.math3.genetics.InvalidRepresentationException;
//import org.junit.Assert;
//import org.junit.Test;
//
//
///**
// * 
// * Test documenting infinite loop on Apache Math3 CycleCrossover.
// * 
// * Please refer to issue "Infinite loop for CycleCrossover with duplicates":
// * https://issues.apache.org/jira/browse/MATH-1451
//
// * @author red
// *
// */
//public class CycleCrossoverDuplicatesTest {
//
//
//	@Test
//    public void testTwoChromosomes(){
//		Chromosome first = new IntegerChromosome(Arrays.asList(8, 4, 7, 3, 6, 2, 5, 1, 9, 0));
//		Chromosome second = new IntegerChromosome(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
//		
//		ChromosomePair pair = new CycleCrossover<Integer>(false).crossover(first, second);
//		
//		IntegerChromosome child1 = new IntegerChromosome(Arrays.asList(8, 1, 2, 3, 4, 5, 6, 7, 9, 0));
//		IntegerChromosome child2 = new IntegerChromosome(Arrays.asList(0, 4, 7, 3, 6, 2, 5, 1, 8, 9));
//		
//		Assert.assertEquals(0, ((IntegerChromosome)pair.getFirst()).compareTo(child1));
//		Assert.assertEquals(0, ((IntegerChromosome)pair.getFirst()).compareTo(child2));
//    }
//
//	@Test
//    public void testTwoChromosomesDuplicates(){
//		Chromosome first = new IntegerChromosome(Arrays.asList(7, 4, 7, 3, 6, 2, 5, 1, 9, 0));
//		Chromosome second = new IntegerChromosome(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 7, 9));
//		
//		ChromosomePair pair = new CycleCrossover<Integer>(false).crossover(first, second);
//		
//		IntegerChromosome child1 = new IntegerChromosome(Arrays.asList(8, 1, 2, 3, 4, 5, 6, 7, 9, 0));
//		IntegerChromosome child2 = new IntegerChromosome(Arrays.asList(0, 4, 7, 3, 6, 2, 5, 1, 8, 9));
//		
//		Assert.assertEquals(0, ((IntegerChromosome)pair.getFirst()).compareTo(child1));
//		Assert.assertEquals(0, ((IntegerChromosome)pair.getFirst()).compareTo(child2));
//    }
//		
//		
//	private static class IntegerChromosome extends AbstractListChromosome<Integer>{
//
//		public IntegerChromosome(List<Integer> chromosomeRepresentation) {
//			super(chromosomeRepresentation);
//		}
//
//		@Override
//		public double fitness() {
//			return 0;
//		}
//
//		@Override
//		protected void checkValidity(List<Integer> chromosomeRepresentation) throws InvalidRepresentationException {
//			// not useful in this example
//		}
//
//		@Override
//		public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> chromosomeRepresentation) {
//			return new IntegerChromosome(chromosomeRepresentation);
//		}
//		
//	}
//	
//}
