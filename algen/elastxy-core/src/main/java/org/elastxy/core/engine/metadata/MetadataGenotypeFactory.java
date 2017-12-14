package org.elastxy.core.engine.metadata;

import java.util.List;
import java.util.SortedMap;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.genotype.Strand;
import org.elastxy.core.engine.core.AlgorithmException;


/**
 * Creates Genotype genes pieces by metadata.
 * @author red
 *
 */
public class MetadataGenotypeFactory {
	private static final Logger logger = Logger.getLogger(MetadataGenotypeFactory.class);


	/**
	 * Create a new gene by metadata
	 * @param metadataCode
	 * @param position
	 * @param metadata
	 * @return
	 */
	private static Gene createGene(String metadataCode, String position) {
		Gene gene = new Gene();
		gene.metadataCode = metadataCode;
		gene.pos = position;
		return gene;
	}
	
	

	/**
	 * Create a list of Genes for all positions
	 * @param positions
	 * @return
	 */
	public static Chromosome createChromosome(MetadataGenoma genoma, List<Allele> alleles){
		Chromosome result = new Chromosome();
		for(int pos=0; pos < genoma.getGenotypeStructure().getPositionsSize(); pos++){
			result.genes.add(createGeneByPosition(genoma, String.valueOf(pos)));
		}

    	IntStream.range(0, result.genes.size()).forEach(i -> result.genes.get(i).allele = alleles.get(i));

		return result;
	}
	

	/**
	 * Create a list of Genes for all positions
	 * @param positions
	 * @return
	 */
	public static Chromosome createChromosome(MetadataGenoma genoma){
		Chromosome result = new Chromosome();
		for(int pos=0; pos < genoma.getGenotypeStructure().getPositionsSize(); pos++){
			result.genes.add(createGeneByPosition(genoma, String.valueOf(pos)));
		}

    	List<Allele> alleles = genoma.getRandomAlleles(result.getPositions());
    	if(alleles.size() < result.genes.size()){
    		throw new AlgorithmException("Number of possible different alleles less than number of genes creating a base predefined Solution. Check if you need the createRandom instead or try adding alleles");
    	} 
    	
    	for(int i=0; i < alleles.size(); i++){
    		result.genes.get(i).allele = alleles.get(i);
    	}

		return result;
	}
	
	/**
	 * Creates a new strand composed by chromosomes given genoma positions,
	 * and populate with random alleles.
	 * 
	 * @param genoma
	 * @return
	 */
	public static Strand createStrand(MetadataGenoma genoma){
		
		// TODOM-2: copy GenotypeStructure instead
		Strand result = new Strand();
//		if(genoma.getNumberOfStrands()!=1){
//			String msg = "Cannot create strand. Genoma with number of strands different from one: "+genoma.getNumberOfStrands();
//			logger.error(msg);
//			throw new AlgorithmException(msg);
//		}
		
		// TODOM-8: all for cycles must not have logics repeated
		for(int c=0; c < genoma.getGenotypeStructure().getNumberOfChromosomes(); c++){
			Chromosome chromosome = new Chromosome();
			for(int g=0; g < genoma.getGenotypeStructure().getNumberOfGenes(c); g++){
				Gene gene = createGeneByPosition(genoma, c+"."+g);
				chromosome.genes.add(gene);
			}
			result.chromosomes.add(chromosome);
		}
		//		IntStream.range(0, genesMetadataByPos.size()).boxed().map(i -> i.toString()).collect(Collectors.toList());

		
		// Assign Alleles
    	SortedMap<String,Allele> alleles = genoma.getRandomAllelesAsMap();
    	result.assignAlleles(alleles);
		
		return result;
	}

	

	/**
	 * Create a new Gene structure without Allele from position
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	private static Gene createGeneByPosition(MetadataGenoma genoma, String position){
		GeneMetadata metadata =  genoma.getMetadataByPosition(position);
		return createGene(metadata.code, position);
	}
	

	
//	/**
//	 * Create a list of Genes from a list of positions
//	 * @param positions
//	 * @return
//	 */
//	public static List<Gene> createSequenceByPositions(MetadataGenoma genoma, List<String> positions){
//		return positions.stream().map(p -> createGeneByPosition(genoma, p)).collect(Collectors.toList());
//	}
	

	
}
