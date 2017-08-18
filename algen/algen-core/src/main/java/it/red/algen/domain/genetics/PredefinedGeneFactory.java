package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.List;

public class PredefinedGeneFactory {
	

	/**
	 * Create a list of Genes for all positions, without Alleles
	 * @param positions
	 * @return
	 */
	public static List<Gene> createSequence(PredefinedGenoma genoma){
		List<Gene> result = new ArrayList<Gene>();
		for(int pos=0; pos < genoma.getPositionsSize(); pos++){
			Gene gene = new Gene();
			gene.pos = String.valueOf(pos);
			result.add(gene);
		}
		return result;
	}
	
}
