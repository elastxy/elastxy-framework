package it.red.algen.domain.genetics;

import it.red.algen.domain.genetics.genotype.Gene;

/**
 * Encode/Decode genoma parts
 * @author red
 *
 */
public interface GenomaTranslator {

	/**
	 * Encode a gene to a specific serializable format
	 * @return
	 */
	public Object encode(Gene gene);
	
	
	/**
	 * Decode a specific serializable format to a gene
	 * @return
	 */
	public Gene decode(Object serializedGene);
	

}
