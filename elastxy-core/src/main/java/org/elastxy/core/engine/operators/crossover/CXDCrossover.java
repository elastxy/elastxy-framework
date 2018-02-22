package org.elastxy.core.engine.operators.crossover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.engine.core.Randomizer;
import org.elastxy.core.engine.operators.Crossover;


/**
 * CXDCrossover (Cycle Crossover with Duplicates) recombinator.
 * 
 * Variant of classic CX crossover, where genes list can have duplicates.
 * 
 * Slightly slower than CX, it's best to use CX when applicable.
 * 
 * NOTE: Implementation is similar to Apache Math3, patched due to following issue.
 * See issue "Infinite loop for CycleCrossover with duplicates":
 * https://issues.apache.org/jira/browse/MATH-1451
 * 
 * No copyright notice is present, due to this reason.
 * 
 * @author red
 *
 */
public class CXDCrossover implements Crossover {

	/**
	 * Takes two offsprings and swap alleles maintaining the same set
	 * of alleles on each offspring.
	 * 
	 * Example (middle pointcut):
	 * (1 * 2 / 3) with (2 / 3 * 1), cut point 2 => (1 *)+(2 /) to be swapped
	 * => Normal crossover: (2/|2/3) (1*|3*1) + [2:1,/:*] old values to be redistributed
	 * => Redistribution
	 * (2/|1/3) (1*|3*1)
	 * (2/|1/3) (1*|3*2)
	 * (2/|1*3) (1*|3*2)
	 * (2/|1*3) (1*|3/2)
	 * 
	 * 
	 * Example (last position):
	 * (1*2/3) with (2/3*1), cut point 4 => (3)+(1) to be swapped
	 * => Normal crossover: (2/3*|3)(1*2/|1)
	 * => Redistribution
	 * (2/3*|3) (1*2/|1)
	 * (2/3*|2) (1*2/|1)
	 * 
	 * TODO1-2: For efficiency, better swapping those in shorter part.
	 * 
	 * @param off0genes
	 * @param off1genes
	 * @param crossoverPoint
	 */
	public static List<Gene>[] recombine(List<Gene> parent1Copy, List<Gene> parent2Copy, Integer crossoverPoint){
		
//		final int firstLength = first.genes.size();
//		final int secondLength = second.genes.size();
//		final int length = firstLength;
//        if (firstLength != secondLength) {
//            throw new IllegalSolutionException("Chromosomes lengths differ: "+firstLength+" vs. "+secondLength);
//        }
//
//        
//        // array representations of the parents
//        final List<Gene> parent1Rep = first.genes;
//        final List<Gene> parent2Rep = second.genes;
        // and of the children: do a crossover copy to simplify the later processing
		
		final int length = parent1Copy.size();
        parent1Copy = new ArrayList<Gene>(parent1Copy); // TODO2-1: optimization: no copy of genes
        final List<Gene> child1Rep = new ArrayList<Gene>(parent1Copy);
        final List<Gene> child2Rep = new ArrayList<Gene>(parent2Copy);

        // the set of all visited indices so far
        final Set<Integer> visitedIndices = new HashSet<Integer>(length);
        // the indices of the current cycle
        final List<Integer> indices = new ArrayList<Integer>(length);

        // determine the starting index
        int idx = crossoverPoint==null || crossoverPoint < 0 ? Randomizer.nextInt(length) : crossoverPoint;
        int cycle = 1;

        while (visitedIndices.size() < length) {
        	
        	// first index
            indices.add(idx);
            Gene item = parent2Copy.get(idx);
            idx = next(parent1Copy, item, idx, length);
            if(idx!=-1) parent1Copy.set(idx, null); // visited

            while (idx!=-1 && idx != indices.get(0)) {
                // add that index to the cycle indices
                indices.add(idx);
                // get the item in the second parent at that index
                item = parent2Copy.get(idx);
                
                // get the index of that item in the first parent
                idx = next(parent1Copy, item, idx, length);
                if(idx!=-1) parent1Copy.set(idx, null); // visited
            }
            parent1Copy.set(indices.get(0), null); // visited

            // for even cycles: swap the child elements on the indices found in this cycle
            if (cycle++ % 2 != 0) {
                for (int i : indices) {
                    Gene tmp = child1Rep.get(i);
                    child1Rep.set(i, child2Rep.get(i));
                    child2Rep.set(i, tmp);
                }
            }

            visitedIndices.addAll(indices);
            // find next starting index: last one + 1 until we find an unvisited index
            idx = (indices.get(0) + 1) % length;
            while (visitedIndices.contains(idx) && visitedIndices.size() < length) {
                idx++;
                if (idx >= length) {
                    idx = 0;
                }
            }
            indices.clear();
        }

        int tot = child1Rep.size();
		for(int i=0; i < tot; i++) parent1Copy.set(i, child1Rep.get(i)); 
        tot = child2Rep.size();
		for(int i=0; i < tot; i++) parent2Copy.set(i, child2Rep.get(i)); 
        return new List[]{parent1Copy, parent2Copy};
	}
	
	
	private static int next(List list, Object item, int from, int length){
		int pos = (from+1) % length;
		while(pos!=from){ // complete loop
			Object found = list.get(pos);
			if(found!=null && found.equals(item)){
				return pos;
			}
			pos = (pos+1) % length; // loop (e.g. 1234 => 34(5) => 12)
		}
		return -1;
	}
}
