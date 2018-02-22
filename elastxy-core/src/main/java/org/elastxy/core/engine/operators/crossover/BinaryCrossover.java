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
package org.elastxy.core.engine.operators.crossover;

import java.util.List;

import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.engine.core.Randomizer;
import org.elastxy.core.engine.operators.Crossover;

/**
 * 
 * Takes two List S1 and S2 as an input and cuts at a crossover point,
 * so that S1 is splitted into S1.a, S1.b, and S2 is splitted into S2.a, S2.b.
 * 
 * Then swaps Genes: S1.a is joined to S2.b, and S1.b to S2.a.
 * 
 * Swap of Genes ends until crossover point EXCLUDED.
 * If crossover point is at the beginning, nothing is done.
 * If crossover point is at the end, only last Gene is swapped.
 * 
 * For example, let's recombine: (1 * 2 / 3) with (4 - 5 + 6).
 * With crossover point 2 (third position):
 * S1.a = 1 *
 * S1.b = 2 / 3
 * S2.a = 4 -
 * S2.b = 5 + 6
 * => (1 * 5 + 6), (4 - 2 / 3)
 * 
 * With crossover point 4 (last position):
 * S1.a = 1 * 2 /
 * S1.b = 3
 * S2.a = 4 - 5 +
 * S2.b = 6
 * => (1 * 2 / 6), (4 - 5 + 3)
 * 
 * @author red
 *
 */
public class BinaryCrossover implements Crossover {

	
	/**
	 * Performs simple binary crossover.
	 * 
	 * @param crossoverPoint - nullable. If null, a random point is taken.
	 */
	public static List<Gene>[] recombine(List<Gene> parent1Copy, List<Gene> parent2Copy, Integer crossoverPoint){
		
		if(crossoverPoint==null){
			crossoverPoint = Randomizer.nextInt(parent1Copy.size());
		}
		
		for(int pos=0; pos < crossoverPoint; pos++){
			Gene tmp = parent1Copy.get(pos);
			parent1Copy.set(pos, parent2Copy.get(pos));
			parent2Copy.set(pos, tmp);
		}
		
		return new List[]{parent1Copy, parent2Copy};
	}
	

}
