package org.elastxy.core.engine.genetics;

import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;

public class PredefinedGenotypeFactory {
	

	/**
	 * Create a Chromosome
	 * @param positions
	 * @return
	 */
	public static Chromosome createGenotype(PredefinedGenoma genoma){
		Chromosome genotype = new Chromosome();
		int tot = genoma.getGenotypeStructure().getPositionsSize();
		for(int pos=0; pos < tot; pos++){
			Gene gene = new Gene();
			gene.pos = String.valueOf(pos);
			genotype.genes.add(gene);
		}
		return genotype;
	}
	
}
