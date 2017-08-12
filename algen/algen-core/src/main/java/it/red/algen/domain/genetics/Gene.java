package it.red.algen.domain.genetics;

import java.util.Map;


/**
 * Gene is a position within a Chromosome, bringing an allele value
 * 
 * TODOM: BuildingBlock concept to optionally group/cluster many genes
 *
 * @author grossi
 */
public class Gene {
	
	/**
	 * Position within solution or chromosome.
	 * 
	 * Position counts only for ordered genotypes:
	 * - for a straight sequence genotype: [00.gene]. E.g "00.32"
	 * - for a ordered chromosome genotype: [chromosome.gene]. E.g "07.10"
	 * 
	 * Otherwise, for unordered genotypes, counts only the chromosome position, if any:
	 * - for an unordered sequence genotype: null
	 * - for an unordered chromosome genotype: [chromosome]. E.g "12"
	 * 
	 */
	public String pos;
	

	/**
	 * Metadata unique code of the Gene
	 */
	public String metadataCode;
	
	
	/**
	 * Location specific properties of the Gene: they are not typed,
	 * as they depend on the structure of the problem.
	 * 
	 * Also, they are not related to the mutating features of the solution (alleles)
	 * but they are intrinsic to the position of the gene in the sequence
	 * and they do not vary over time.
	 * 
	 * E.g. the umidity, temperature and wind properties for a location in the garden terrace
	 */
	public Map<String,Object> locationProperties;

	
	/**
	 * The value the Gene assumes currently
	 */
	public Allele allele;
	
	
	
	public String encode() {
		return allele.encode();
	}
	
	
	public Gene copy(){
		Gene result = new Gene();
		result.pos = pos;
		result.metadataCode = metadataCode;
		result.allele = allele.copy();
		result.locationProperties = locationProperties; // NOTE: we assume they are immutable! 
		return result;
	}
	
	public String toString(){
		return String.format("[%s:%s=>%s]", pos, metadataCode, allele!=null?allele.value:null);
	}

}
