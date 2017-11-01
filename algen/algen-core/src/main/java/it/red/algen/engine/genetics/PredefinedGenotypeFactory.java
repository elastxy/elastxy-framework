package it.red.algen.engine.genetics;

import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.genotype.Gene;

public class PredefinedGenotypeFactory {
	

	/**
	 * Create a Chromosome
	 * @param positions
	 * @return
	 */
	public static Chromosome createGenotype(PredefinedGenoma genoma){
		Chromosome genotype = new Chromosome();
		for(int pos=0; pos < genoma.getGenotypeStructure().getPositionsSize(); pos++){
			Gene gene = new Gene();
			gene.pos = String.valueOf(pos);
			genotype.genes.add(gene);
		}
		return genotype;
	}
	
}
