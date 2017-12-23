package org.elastxy.core.engine.operators.crossover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.engine.core.Randomizer;
import org.elastxy.core.engine.operators.Crossover;


/**
 * See CXDCrossover explanations, but without admitting duplicates.
 * 
 * TODOM: link with Apache math3 library. Check no duplicates in input.
 * 
 * @author red
 *
 */
public class CXCrossover implements Crossover {

	public static List<Gene>[] recombine(List<Gene> parent1Copy, List<Gene> parent2Copy, Integer crossoverPoint) {
        
		final int length = parent1Copy.size();
        if (length != parent2Copy.size()) {
            throw new AlgorithmException("Cannot recombine two parents with different length: "+parent2Copy.size()+" vs. "+length);
        }

        // do a crossover copy to simplify the later processing
        final List<Gene> child1Rep = new ArrayList<Gene>(parent2Copy);
        final List<Gene> child2Rep = new ArrayList<Gene>(parent1Copy);

        // the set of all visited indices so far
        final Set<Integer> visitedIndices = new HashSet<Integer>(length);
        // the indices of the current cycle
        final List<Integer> indices = new ArrayList<Integer>(length);

        // determine the starting index
        int idx = crossoverPoint==null||crossoverPoint<0 ? Randomizer.nextInt(length) : 0;
        int cycle = 1;

        while (visitedIndices.size() < length) {
            indices.add(idx);

            Gene item = parent2Copy.get(idx);
            idx = parent1Copy.indexOf(item);

            while (idx != indices.get(0)) {
                // add that index to the cycle indices
                indices.add(idx);
                // get the item in the second parent at that index
                item = parent2Copy.get(idx);
                // get the index of that item in the first parent
                idx = parent1Copy.indexOf(item);
            }

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
}
