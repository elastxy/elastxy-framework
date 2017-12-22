package org.elastxy.core.engine.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.engine.core.Randomizer;

public class CXRecombinator {
	
	public static List<Gene>[] recombine(List<Gene> parent1Rep, List<Gene> parent2Rep, Integer crossoverPoint) {
		
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
		
		final int length = parent1Rep.size();
        parent1Rep = new ArrayList<Gene>(parent1Rep); // TODOA: optimization: no copy of genes
        final List<Gene> child1Rep = new ArrayList<Gene>(parent1Rep);
        final List<Gene> child2Rep = new ArrayList<Gene>(parent2Rep);

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
            Gene item = parent2Rep.get(idx);
            idx = next(parent1Rep, item, idx, length);
            if(idx!=-1) parent1Rep.set(idx, null); // visited

            while (idx!=-1 && idx != indices.get(0)) {
                // add that index to the cycle indices
                indices.add(idx);
                // get the item in the second parent at that index
                item = parent2Rep.get(idx);
                
                // get the index of that item in the first parent
                idx = next(parent1Rep, item, idx, length);
                if(idx!=-1) parent1Rep.set(idx, null); // visited
            }
            parent1Rep.set(indices.get(0), null); // visited

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

        return new List[]{child1Rep, child2Rep};
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
