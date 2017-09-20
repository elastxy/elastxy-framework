package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.genotype.Gene;


/**
 * Creates Genotype genes pieces by metadata.
 * @author red
 *
 */
public class MetadataGeneticMaterialFactory {
	private static final Logger logger = Logger.getLogger(MetadataGeneticMaterialFactory.class);


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
	public static List<Gene> createSequence(MetadataGenoma genoma){
		List<Gene> result = new ArrayList<Gene>();
		for(int pos=0; pos < genoma.getGenotypeStructure().getPositionsSize(); pos++){
			result.add(createGeneByPosition(genoma, String.valueOf(pos)));
		}
		return result;
	}
	
	
	/**
	 * Creates a new strand composed by chromosomes given genoma positions
	 * @param genoma
	 * @return
	 */
	public static List<Chromosome> createStrand(MetadataGenoma genoma){
		List<Chromosome> result = new ArrayList<Chromosome>();
//		if(genoma.getNumberOfStrands()!=1){
//			String msg = "Cannot create strand. Genoma with number of strands different from one: "+genoma.getNumberOfStrands();
//			logger.error(msg);
//			throw new AlgorithmException(msg);
//		}
		
		for(int c=0; c < genoma.getGenotypeStructure().getNumberOfChromosomes(); c++){
			Chromosome chromosome = new Chromosome();
			for(int g=0; g < genoma.getGenotypeStructure().getNumberOfGenes(c); g++){
				Gene gene = createGeneByPosition(genoma, c+"."+g);
				chromosome.genes.add(gene);
			}
			result.add(chromosome);
		}
		//		IntStream.range(0, genesMetadataByPos.size()).boxed().map(i -> i.toString()).collect(Collectors.toList());
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
