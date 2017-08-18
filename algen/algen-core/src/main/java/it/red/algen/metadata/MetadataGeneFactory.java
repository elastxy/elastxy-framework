package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.MetadataGenoma;

public class MetadataGeneFactory {

	/**
	 * Create a new Gene structure without Allele from metadata
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	public static Gene createGeneByCode(MetadataGenoma genoma, String metadataCode, String position){
		GeneMetadata metadata =  genoma.getMetadataByCode(metadataCode);
		return createGene(metadataCode, position, metadata);
	}


	/**
	 * Create a new Gene structure without Allele from position
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	public static Gene createGeneByPosition(MetadataGenoma genoma, String position){
		GeneMetadata metadata =  genoma.getMetadataByPosition(position);
		return createGene(metadata.code, position, metadata);
	}

	
	/**
	 * Create a list of Genes from a list of positions
	 * @param positions
	 * @return
	 */
	public static List<Gene> createSequenceByPositions(MetadataGenoma genoma, List<String> positions){
		return positions.stream().map(p -> createGeneByPosition(genoma, p)).collect(Collectors.toList());
	}
	

	/**
	 * Create a list of Genes for all positions
	 * @param positions
	 * @return
	 */
	public static List<Gene> createSequence(MetadataGenoma genoma){
		List<Gene> result = new ArrayList<Gene>();
		for(int pos=0; pos < genoma.getPositionsSize(); pos++){
			result.add(createGeneByPosition(genoma, String.valueOf(pos)));
		}
		return result;
	}
	
	private static Gene createGene(String metadataCode, String position, GeneMetadata metadata) {
		Gene gene = new Gene();
		gene.metadataCode = metadataCode;
		gene.pos = position;
		gene.locationProperties = new HashMap<String, Object>(metadata.userProperties);
		return gene;
	}

}
