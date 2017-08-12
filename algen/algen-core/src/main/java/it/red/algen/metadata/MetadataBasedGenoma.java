package it.red.algen.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.engine.AlleleGenerator;

public class MetadataBasedGenoma implements Genoma {

	/**
	 * Metadata of all genes type, indexed by code
	 */
	public Map<String,GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
	

	/**
	 * Metadata of all genes type, indexed by position, if order is important
	 */
	public Map<String,GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();


	private AlleleGenerator alleleGenerator;

	@Override
	public void setupAlleleGenerator(AlleleGenerator generator){
		alleleGenerator = generator;
	}

	
	/**
	 * Creates a new random allele given the position in the sequence
	 * TODO: if not ordered, metadata is random
	 */
	@Override
	public Allele createRandomAllele(String position) {
		return alleleGenerator.generate(getMetadataByPosition(position));
	}

	
	/**
	 * Generate a new set of random Alleles based on metadata codes
	 * @param metadataCodes
	 * @return
	 */
	@Override
	public List<Allele> createRandomAlleles(List<String> positions){
		return positions.stream().map(s -> createRandomAllele(s)).collect(Collectors.toList());
	}

	
	/**
	 * Generate a new Allele based on a metadata
	 * @param metadataCode
	 * @return
	 */
	public Allele createRandomAlleleByCode(String metadataCode){
		return alleleGenerator.generate(getMetadataByCode(metadataCode));
	}

	
	/**
	 * Generate new Allele list based on some metadata
	 * @param metadataCode
	 * @return
	 */
	public List<Allele> createRandomAllelesByCode(List<String> metadataCodes){
		return metadataCodes.stream().map(s -> createRandomAlleleByCode(s)).collect(Collectors.toList());
	}

	
	/**
	 * Generates a new Allele based on specific value
	 * 
	 * An exception is raise if value is not present between metadata available values
	 */
	public Allele createPredefinedAlleleByValue(String metadataCode, Object value){
		return alleleGenerator.generate(getMetadataByCode(metadataCode), value);
	}
	

	/**
	 * Create a new Gene structure without Allele from metadata
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	public Gene createGeneByCode(String metadataCode, String position){
		GeneMetadata metadata =  getMetadataByCode(metadataCode);
		return createGene(metadataCode, position, metadata);
	}


	/**
	 * Create a new Gene structure without Allele from position
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	public Gene createGeneByPosition(String position){
		GeneMetadata metadata =  getMetadataByPosition(position);
		return createGene(metadata.code, position, metadata);
	}

	
	private Gene createGene(String metadataCode, String position, GeneMetadata metadata) {
		Gene gene = new Gene();
		gene.metadataCode = metadataCode;
		gene.pos = position;
		gene.locationProperties = new HashMap<String, Object>(metadata.userProperties);
		return gene;
	}
	

	/**
	 * Create a list of Genes from a list of positions
	 * @param positions
	 * @return
	 */
	public List<Gene> createSequenceByPositions(List<String> positions){
		return positions.stream().map(p -> createGeneByPosition(p)).collect(Collectors.toList());
	}

	
	/**
	 * Get the metadata by code
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByCode(String metadataCode){
		return genesMetadataByCode.get(metadataCode);
	}
	
	/**
	 * Get the metadata by a given position
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByPosition(String position){
		return genesMetadataByPos.get(position);
	}
	
	
}
